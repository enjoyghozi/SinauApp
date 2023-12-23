package com.example.sinauapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sinauapp.ui.theme.gradient1
import com.example.sinauapp.ui.theme.gradient2
import com.example.sinauapp.ui.theme.gradient3
import com.example.sinauapp.ui.theme.gradient4
import com.example.sinauapp.ui.theme.gradient5

@Entity
data class Mapel(
    val name: String,
    val goalHours: Float,
    val colors: List<Int>,
    @PrimaryKey(autoGenerate = true)
    val mapelId: Int? = null
) {
    companion object {
        val mapelCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
