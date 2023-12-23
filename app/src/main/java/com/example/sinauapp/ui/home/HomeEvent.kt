package com.example.sinauapp.ui.home

import androidx.compose.ui.graphics.Color
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task

sealed class HomeEvent {
    data object SaveMapel : HomeEvent()
    data object DeleteSession : HomeEvent()
    data class OnDeleteSessionButtonClick(val session: Session): HomeEvent()
    data class OnTaskIsCompleteChange(val task: Task): HomeEvent()
    data class OnMapelCardColorChange(val colors: List<Color>): HomeEvent()
    data class OnMapelNameChange(val name: String): HomeEvent()
    data class OnGoalStudyHoursChange(val hours: String): HomeEvent()
}
