package com.example.sinauapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToMapel: String,
    val isStatus: Boolean,
    val taskMapelId: Int,
    @PrimaryKey(autoGenerate = true)
    val taskId: Int? = null
)
