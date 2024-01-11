package com.example.sinauapp.ui.session

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.SessionRepository
import com.example.sinauapp.utility.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    mapelRepository: MapelRepository,
    private val sessionRepository: SessionRepository
): ViewModel() {
    private val _state = MutableStateFlow(SessionState())
    val state = combine(
        _state,
        mapelRepository.getAllMapel(),
        sessionRepository.getAllSessions()
    ) { state, mapel, sessions ->
        state.copy(
            mapel = mapel,
            sessions = sessions
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = SessionState()
    )

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()


    fun onEvent(event: SessionEvent) {
        when(event) {
            SessionEvent.CheckMapelId -> {}
            SessionEvent.DeleteSession -> deleteSession()
            is SessionEvent.OnDeleteSessionButtonClick -> {}
            is SessionEvent.OnRelatedToMapelChange -> {
                _state.update {
                    it.copy(
                        relatedToMapel = event.mapel.name,
                        mapelId = event.mapel.mapelId
                    )
                }
            }
            is SessionEvent.SaveSession -> insertSession(event.duration)
            is SessionEvent.UpdateMapelIdAndRelatedToMapel -> {}
        }
    }

    private fun deleteSession() {
        viewModelScope.launch {
            try {
                state.value.session?.let {
                    sessionRepository.deleteSession(it)
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "Sesi belajar berhasil di hapus")
                    )
                }
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Tidak bisa menghapus sesi. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    private fun insertSession(duration: Long) {
        viewModelScope.launch {
            if (duration < 59) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Durasi sesi minimal 59 detik",
                    )
                )
                return@launch
            }
            try {
                sessionRepository.insertSession(
                    session = Session(
                        sessionMapelId = state.value.mapelId ?: -1,
                        relatedToMapel = state.value.relatedToMapel ?: "",
                        date = Instant.now().toEpochMilli(),
                        duration = duration
                    )
                )
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Sesi berhasil disimpan")
                )
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Tidak bisa menyimpan sesi. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }
}