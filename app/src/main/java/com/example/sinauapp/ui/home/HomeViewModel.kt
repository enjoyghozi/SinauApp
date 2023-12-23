package com.example.sinauapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.SessionRepository
import com.example.sinauapp.utility.toHours
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
}