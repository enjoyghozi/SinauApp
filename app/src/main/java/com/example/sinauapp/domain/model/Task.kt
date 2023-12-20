package com.example.sinauapp.domain.model

data class Task(
    val title: String,
    val description: String,
    val dueDate: String,
    val priority: String,
    val relatedToMapel: String,
    val isStatus: Boolean
)
