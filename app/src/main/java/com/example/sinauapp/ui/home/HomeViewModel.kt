package com.example.sinauapp.ui.home

import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.SessionRepository
import com.example.sinauapp.utility.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mapelRepository: MapelRepository,
    private val sessionRepository: SessionRepository
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

            HomeEvent.DeleteSession -> TODO()

            is HomeEvent.OnTaskIsCompleteChange -> TODO()

        }
    }

    private fun saveMapel() {
        viewModelScope.launch {
            mapelRepository.upsertMapel(
                mapel = Mapel(
                    name = _state.value.mapelName,
                    goalHours = _state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                    colors = _state.value.mapelCardColors.map { it.toArgb() },
                )
            )
        }
    }

}