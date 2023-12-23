package com.example.sinauapp.data.repository

import com.example.sinauapp.data.local.TaskDao
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepoImpl @Inject constructor(
    private val taskDao: TaskDao
): TaskRepository {

    override suspend fun upsertTask(task: Task) {
        taskDao.upsertTask(task)
    }

    override suspend fun deleteTask(taskId: Int) {
        taskDao.deleteTask(taskId)
    }

    override suspend fun getTaskById(taskId: Int): Task? {
        return taskDao.getTaskById(taskId)
    }

    override fun getUpcomingTasksForMapel(mapelId: Int): Flow<List<Task>> {
        return taskDao.getTasksForMapel(mapelId)
            .map { tasks -> tasks.filter { it.isStatus.not() } }
            .map { tasks -> sortTasks(tasks) }
    }

    override fun getCompletedTasksForMapel(mapelId: Int): Flow<List<Task>> {
        return taskDao.getTasksForMapel(mapelId)
            .map { tasks -> tasks.filter { it.isStatus } }
            .map { tasks -> sortTasks(tasks) }
    }

    override fun getAllUpcomingTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
            .map { tasks -> tasks.filter { it.isStatus.not() } }
            .map { tasks -> sortTasks(tasks) }
    }

    private fun sortTasks(tasks: List<Task>): List<Task> {
        return tasks.sortedWith(compareBy<Task> { it.dueDate }.thenByDescending { it.priority })
    }
}