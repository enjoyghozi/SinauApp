package com.example.sinauapp.ui.task

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.TaskRepository
import com.example.sinauapp.ui.navArgs
import com.example.sinauapp.utility.Priority
import com.example.sinauapp.utility.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val mapelRepository: MapelRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val navArgs: TaskScreenNavArgs = savedStateHandle.navArgs()

    private val _state = MutableStateFlow(TaskState())
    val state = combine(
        _state,
        mapelRepository.getAllMapel()
    ) { state, mapel ->
        state.copy(mapel = mapel)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
        initialValue = TaskState()
    )

    /* Snackbar */
    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    /* init */
    init {
        fetchTask()
        fetchMapel()
    }

    /* Function to handle event */
    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.OnTitleChange -> {
                _state.update {
                    it.copy(title = event.title)
                }
            }

            is TaskEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(description = event.description)
                }
            }

            is TaskEvent.OnDateChange -> {
                _state.update {
                    it.copy(dueDate = event.millis)
                }
            }

            is TaskEvent.OnPriorityChange -> {
                _state.update {
                    it.copy(priority = event.priority)
                }
            }

            TaskEvent.OnIsCompleteChange -> {
                _state.update {
                    it.copy(isTaskComplete = !_state.value.isTaskComplete)
                }
            }

            is TaskEvent.OnRelatedMapelSelect -> {
                _state.update {
                    it.copy(
                        relatedToMapel = event.mapel.name,
                        mapelId = event.mapel.mapelId
                    )
                }
            }

            TaskEvent.SaveTask -> saveTask()
            TaskEvent.DeleteTask -> deleteTask()
        }
    }

    /* Function to handle Delete */
    private fun deleteTask() {
        viewModelScope.launch {
            try {
                val currentTaskId = state.value.currentTaskId
                if (currentTaskId != null) {
                    withContext(Dispatchers.IO) {
                        taskRepository.deleteTask(taskId = currentTaskId)
                    }
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "Task deleted successfully")
                    )
                    _snackbarEventFlow.emit(SnackbarEvent.NavigateUp)
                } else {
                    _snackbarEventFlow.emit(
                        SnackbarEvent.ShowSnackbar(message = "No Task to delete")
                    )
                }
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Couldn't delete task. ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    /* Function to handle Save */
    private fun saveTask() {
        viewModelScope.launch {
            val state = _state.value
            if (state.mapelId == null || state.relatedToMapel == null) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Mohon pilih mapel terlebih dahulu",
                    )
                )
                return@launch
            }
            try {
                taskRepository.upsertTask(
                    task = Task(
                        title = state.title,
                        description = state.description,
                        dueDate = state.dueDate ?: Instant.now().toEpochMilli(),
                        relatedToMapel = state.relatedToMapel,
                        priority = state.priority.value,
                        isComplete = state.isTaskComplete,
                        taskMapelId = state.mapelId,
                        taskId = state.currentTaskId
                    )
                )
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(message = "Tugas berhasil di tambahkan")
                )
                _snackbarEventFlow.emit(SnackbarEvent.NavigateUp)
            } catch (e: Exception) {
                _snackbarEventFlow.emit(
                    SnackbarEvent.ShowSnackbar(
                        message = "Tidak dapat menambahkan tugas \n Message: ${e.message}",
                        duration = SnackbarDuration.Long
                    )
                )
            }
        }
    }

    /* Function to fetch task */
    private fun fetchTask() {
        viewModelScope.launch {
            navArgs.taskId?.let { id ->
                taskRepository.getTaskById(id)?.let { task ->
                    _state.update {
                        it.copy(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isTaskComplete = task.isComplete,
                            relatedToMapel = task.relatedToMapel,
                            priority = Priority.fromInt(task.priority),
                            mapelId = task.taskMapelId,
                            currentTaskId = task.taskId
                        )
                    }
                }
            }
        }
    }

    /* Function to fetch mapel */
    private fun fetchMapel() {
        viewModelScope.launch {
            navArgs.mapelId?.let { id ->
                mapelRepository.getMapelById(id)?.let { mapel ->
                    _state.update {
                        it.copy(
                            mapelId = mapel.mapelId,
                            relatedToMapel = mapel.name
                        )
                    }
                }
            }
        }
    }
}