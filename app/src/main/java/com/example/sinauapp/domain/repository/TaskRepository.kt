package com.example.sinauapp.domain.repository

import com.example.sinauapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun upsertTask(task: Task)

    suspend fun deleteTask(taskId: Int)

    suspend fun getTaskById(taskId: Int): Task?

    fun getUpcomingTasksForMapel(mapelId: Int): Flow<List<Task>>

    fun getCompletedTasksForMapel(mapelId: Int): Flow<List<Task>>

    fun getAllUpcomingTasks(): Flow<List<Task>>
}