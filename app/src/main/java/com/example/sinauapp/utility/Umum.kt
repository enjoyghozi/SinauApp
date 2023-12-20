package com.example.sinauapp.utility

import androidx.compose.ui.graphics.Color
import com.example.sinauapp.ui.theme.green
import com.example.sinauapp.ui.theme.orange
import com.example.sinauapp.ui.theme.red

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
