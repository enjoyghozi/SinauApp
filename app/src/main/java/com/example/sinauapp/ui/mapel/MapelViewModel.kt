package com.example.sinauapp.ui.mapel

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.SessionRepository
import com.example.sinauapp.domain.repository.TaskRepository
import com.example.sinauapp.ui.navArgs
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
class MapelViewModel @Inject constructor(
    private val mapelRepository: MapelRepository,
    private val sessionRepository: SessionRepository,
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
    ): ViewModel() {

    private val navArgs: MapelScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(MapelState())
    val state = combine(
        _state,
        taskRepository.getUpcomingTasksForMapel(navArgs.mapelId),
        taskRepository.getCompletedTasksForMapel(navArgs.mapelId),
        sessionRepository.getRecentTenSessionsForMapel(navArgs.mapelId),
        sessionRepository.getTotalSessionsDurationByMapel(navArgs.mapelId)
    ) { state, upcomingTasks, completedTasks, recentSessions, totalSessionsDuration ->
        state.copy(
            upcomingTasks = upcomingTasks,
            completedTasks = completedTasks,
            recentSessions = recentSessions,
            studiedHours = totalSessionsDuration.toHours()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MapelState()
    )

    init {
        fetchMapel()
    }

    fun onEvent(event: MapelEvent) {
        when(event) {

            /* Color change event */
            is MapelEvent.OnMapelCardColorChange -> {
                _state.update { it.copy(mapelCardColors = event.color) }
            }

            /* Mapel name change event */
            is MapelEvent.OnMapelNameChange -> {
                _state.update { it.copy(mapelName = event.name) }
            }

            /* Goal study hours change event */
            is MapelEvent.OnGoalStudyHoursChange -> {
                _state.update { it.copy(goalStudyHours = event.hours) }
            }

            /* Update Mapel event */
            MapelEvent.UpdateMapel -> {
                viewModelScope.launch {
                    mapelRepository.upsertMapel(
                        mapel = Mapel(
                            mapelId = state.value.currentMapelId,
                            name = state.value.mapelName,
                            goalHours = state.value.goalStudyHours.toFloatOrNull() ?: 1f,
                            colors = state.value.mapelCardColors.map { it.toArgb() }
                        )
                    )
                }
            }

            MapelEvent.DeleteMapel -> TODO()
            MapelEvent.DeleteSession -> TODO()
            is MapelEvent.OnDeleteSessionButtonClick -> TODO()
            is MapelEvent.OnTaskIsCompleteChange -> TODO()
            MapelEvent.UpdateProgress -> TODO()
        }
    }

    /* functions fetch Mapel */
    private fun fetchMapel() {
        viewModelScope.launch {
            mapelRepository
                .getMapelById(navArgs.mapelId)?.let { mapel ->
                    _state.update {
                        it.copy(
                            mapelName = mapel.name,
                            goalStudyHours = mapel.goalHours.toString(),
                            mapelCardColors = mapel.colors.map { Color(it) },
                            currentMapelId = mapel.mapelId
                        )
                    }
                }
        }
    }
}