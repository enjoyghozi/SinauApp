package com.example.sinauapp.utility

import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import com.example.sinauapp.ui.theme.green
import com.example.sinauapp.ui.theme.orange
import com.example.sinauapp.ui.theme.red
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority (
    val title: String,
    val color: Color,
    val Value: Int
) {
    Minor("Minor", color = green, 0),
    Major("Major", color = orange, 1),
    Important("Important", color = red, 2);

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.Value == value } ?: Major
    }
}

fun Long?.changeMillisToDateString(): String {
    val date: LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now()
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

fun Long.toHours(): Float {
    val hours = this.toFloat() / 3600f
    return "%.2f".format(hours).toFloat()
}

sealed class SnackbarEvent {
    data class ShowSnackbar(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : SnackbarEvent()

    data object NavigateUp : SnackbarEvent()
}