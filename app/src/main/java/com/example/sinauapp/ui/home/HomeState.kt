package com.example.sinauapp.ui.home

import androidx.compose.ui.graphics.Color
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session

data class HomeState(
    val totalMapelCount: Int = 0,
    val totalStudiedHours: Float = 0f,
    val totalGoalStudyHours: Float = 0f,
    val mapel: List<Mapel> = emptyList(),
    val mapelName: String = "",
    val goalStudyHours: String = "",
    val mapelCardColors: List<Color> = Mapel.mapelCardColors.random(),
    val session: Session? = null
)
