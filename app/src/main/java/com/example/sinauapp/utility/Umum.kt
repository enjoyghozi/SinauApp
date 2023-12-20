package com.example.sinauapp.utility

import androidx.compose.ui.graphics.Color

enum class Priority (
    val title: String,
    val color: Color,
    val Value: Int
) {
    Minor("Minor", color = Color.Green, 0),
    Major("Major", color = Color.Yellow, 1),
    Important("Important", color = Color.Red, 2);

    companion object {
        fun fromInt(value: Int) = values().firstOrNull { it.Value == value } ?: Major
    }
}
