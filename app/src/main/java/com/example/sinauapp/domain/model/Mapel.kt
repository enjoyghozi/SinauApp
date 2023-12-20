package com.example.sinauapp.domain.model

import androidx.compose.ui.graphics.Color
import com.example.sinauapp.ui.theme.gradient1
import com.example.sinauapp.ui.theme.gradient2
import com.example.sinauapp.ui.theme.gradient3
import com.example.sinauapp.ui.theme.gradient4
import com.example.sinauapp.ui.theme.gradient5

data class Mapel(
    val name: String,
    val goalHours: Float,
    val colors: List<Color>,
    val mapelId: Int
) {
    companion object {
        val mapelCardColor = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
