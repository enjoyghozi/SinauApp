package com.example.sinauapp.domain.model

data class Task(
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToMapel: String,
    val isStatus: Boolean,
    val taskMapelId: Int,
    val taskId: Int
)
