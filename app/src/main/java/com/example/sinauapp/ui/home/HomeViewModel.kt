package com.example.sinauapp.ui.home

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.SessionRepository
import com.example.sinauapp.domain.repository.TaskRepository
import com.example.sinauapp.utility.SnackbarEvent
import com.example.sinauapp.utility.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mapelRepository: MapelRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository
): ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        mapelRepository.getTotalMapelCount(),
        mapelRepository.getAllMapel(),
        mapelRepository.getTotalGoalHours(),
        sessionRepository.getTotalSessionsDuration()
    ) { state, mapelCount, mapel, goalHours, totalSessionsDuration ->
        state.copy(
            totalMapelCount = mapelCount,
            totalStudiedHours = totalSessionsDuration.toHours(),
            totalGoalStudyHours = goalHours,
            mapel = mapel
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    /* Task List (Catatan Tugas) */
    val tasks: StateFlow<List<Task>> = taskRepository.getAllUpcomingTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /* Recent Sessions */
    val recentSessions: StateFlow<List<Session>> = sessionRepository.getRecentFiveSessions()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /* Snackbar */
    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()



    /* Event Implementation */
    fun onEvent(event: HomeEvent) {
        when(event) {

            /* Event for Update Mapel */
            is HomeEvent.OnMapelNameChange -> {
                _state.update {
                    it.copy(
                        mapelName = event.name
                    )
                }
            }

            /* Event for Update Goal Study Hours */
            is HomeEvent.OnGoalStudyHoursChange -> {
                _state.update {
                    it.copy(
                        goalStudyHours = event.hours
                    )
                }
            }

            /* Event for change color of Mapel */
            is HomeEvent.OnMapelCardColorChange -> {
                _state.update {
                    it.copy(
                        mapelCardColors = event.colors
                    )
                }
            }

            /* Event for Delete Session */
            is HomeEvent.OnDeleteSessionButtonClick -> {
                _state.update {
                    it.copy(
                        session = event.session
                    )
                }
            }

            /* Event for Save Mapel */
            HomeEvent.SaveMapel -> saveMapel()

            /* Event for Delete Session */
            HomeEvent.DeleteSession -> TODO()

            /* Event for Task isComplete */
            is HomeEvent.OnTaskIsCompleteChange -> {
                updateTask(event.task)
            }

        }
    }

    /* Function for Update Task to Completed */
    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.upsertTask(
                    task = task.copy(isComplete = !task.isComplete)
                )
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Saved in completed tasks.")
                )
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        "Couldn't update task. ${e.message}",
                        SnackbarDuration.Long
                    )
                )
            }
        }
    }

    /* Function for Save Mapel */
    private fun saveMapel() {
        viewModelScope.launch {
            try {
                mapelRepository.upsertMapel(
                    mapel = Mapel(
                        name = _state.value.mapelName,
                        goalHours = _state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                        colors = _state.value.mapelCardColors.map { it.toArgb() },
                    )
                )
                _state.update {
                    it.copy(
                        mapelName = "",
                        goalStudyHours = "",
                        mapelCardColors = Mapel.mapelCardColors.random()
                    )
                }
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Mapel berhasil disimpan",
                        SnackbarDuration.Long
                    )
                )
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Tidak bisa menyimpan Mapel. ${e.message}",
                    )
                )
            }
        }
    }

}