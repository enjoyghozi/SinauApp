package com.example.sinauapp.ui.mapel

import androidx.compose.ui.graphics.Color
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task

sealed class MapelEvent{
    data object UpdateMapel : MapelEvent()
    data object DeleteMapel : MapelEvent()
    data object DeleteSession : MapelEvent()
    data object UpdateProgress : MapelEvent()
    data class OnTaskIsCompleteChange(val task: Task): MapelEvent()
    data class OnMapelCardColorChange(val color: List<Color>): MapelEvent()
    data class OnMapelNameChange(val name: String): MapelEvent()
    data class OnGoalStudyHoursChange(val hours: String): MapelEvent()
    data class OnDeleteSessionButtonClick(val session: Session): MapelEvent()
}
