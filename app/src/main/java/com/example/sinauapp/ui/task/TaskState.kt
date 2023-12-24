package com.example.sinauapp.ui.task

import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.utility.Priority

data class TaskState(
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isTaskComplete: Boolean = false,
    val priority: Priority = Priority.Minor,
    val relatedToMapel: String? = null,
    val mapel: List<Mapel> = emptyList(),
    val mapelId: Int? = null,
    val currentTaskId: Int? = null
)
