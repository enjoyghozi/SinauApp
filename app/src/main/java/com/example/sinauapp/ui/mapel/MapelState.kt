package com.example.sinauapp.ui.mapel

import androidx.compose.ui.graphics.Color
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task

data class MapelState(
    val currentMapelId: Int? = null,
    val mapelName: String = "",
    val goalStudyHours: String = "",
    val mapelCardColors: List<Color> = Mapel.mapelCardColors.random(),
    val studiedHours: Float = 0f,
    val progress: Float = 0f,
    val recentSessions: List<Session> = emptyList(),
    val upcomingTasks: List<Task> = emptyList(),
    val completedTasks: List<Task> = emptyList(),
    val session: Session? = null
)
