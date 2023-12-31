package com.example.sinauapp.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sinauapp.ui.components.DeleteDialog
import com.example.sinauapp.ui.components.MapelListBottomSheet
import com.example.sinauapp.ui.components.TaskCheckbox
import com.example.sinauapp.ui.components.TaskDatePicker
import com.example.sinauapp.utility.Priority
import com.example.sinauapp.utility.SnackbarEvent
import com.example.sinauapp.utility.changeMillisToDateString
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.Instant

data class TaskScreenNavArgs(
    val taskId: Int?,
    val mapelId: Int?
)

/* Route */
@Destination(navArgsDelegate = TaskScreenNavArgs::class)
@Composable
fun TaskScreenRoute(
    navigator: DestinationsNavigator
) {
    val viewModel: TaskViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    TaskScreen(
        state = state,
        onEvent = viewModel::onEvent,
        snackbarEvent = viewModel.snackbarEventFlow,
        onBackButtonClick = {
            navigator.navigateUp()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreen(
    state: TaskState,
    onEvent: (TaskEvent) -> Unit,
    snackbarEvent: SharedFlow<SnackbarEvent>,
    onBackButtonClick: () -> Unit
) {

    /* Variables */
    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    /* Form Validation */
    var taskTitleError by remember { mutableStateOf<String?>(null) }
    taskTitleError = when {
        state.title.isBlank() -> "Silahkan isi judul tugas"
        state.title.length < 3 -> "Judul tugas minimal 3 karakter"
        state.title.length > 30 -> "Judul tugas terlalu panjang"
        else -> null
    }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        snackbarEvent.collectLatest { event ->
            when (event) {
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }

                SnackbarEvent.NavigateUp -> {
                    onBackButtonClick()
                }
            }
        }
    }

    /* Delete Dialog */
    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Hapus Task?",
        bodyText = "Apakah anda yakin ingin menghapus task ini?" + "\n" + "Task yang di hapus tidak dapat dikembalikan",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = {
            onEvent(TaskEvent.DeleteTask)
            isDeleteDialogOpen = false
        }
    )

    /* Date Picker */
    TaskDatePicker(
        state = datePickerState,
        isOpen = isDatePickerDialogOpen,
        onDismissRequest = { isDatePickerDialogOpen = false },
        onConfirmButtonClicked = {
            onEvent(TaskEvent.OnDateChange(millis = datePickerState.selectedDateMillis))
            isDatePickerDialogOpen = false
        }
    )

    /* Mapel Bottom Sheet */
    MapelListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        mapel = state.mapel,
        onDismissRequest = { isBottomSheetOpen = false },
        onMapelClicked = { mapel ->
            isBottomSheetOpen = false
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) isBottomSheetOpen = false
            }
            onEvent(TaskEvent.OnRelatedMapelSelect(mapel))
        }
    )

    /* Load Content */
    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar =  {
            TaskScreenTopBar(
                isTaskExist = state.currentTaskId != null,
                isComplete = false,
                checkBoxBorderColor = state.priority.color,
                onBackButtonClick = onBackButtonClick,
                onDeleteButtonClick = { isDeleteDialogOpen = true },
                onCheckBoxClick = { onEvent(TaskEvent.OnIsCompleteChange) },
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .verticalScroll(state = rememberScrollState())
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 12.dp)
        ) {

            /* Text Field Judul */
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.title,
                onValueChange = { onEvent(TaskEvent.OnTitleChange(it)) },
                label = {
                    Text(text = "Judul")
                },
                singleLine = true,
                isError = taskTitleError != null && state.title.isNotBlank(),
                supportingText = {
                    Text(text = taskTitleError.orEmpty())
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            /* Text Field Keterangan */
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.description,
                onValueChange = { onEvent(TaskEvent.OnDescriptionChange(it)) },
                label = {
                    Text(text = "Keterangan")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            /* Datepicker Tanggal */
            Text(
                text = "Tanggal",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = datePickerState.selectedDateMillis.changeMillisToDateString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { isDatePickerDialogOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Pilih Tanggal",
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            /* Color for Prioritas */
            Text(
                text = "Prioritas",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Priority.entries.forEach { priority ->
                    PriorityButton(
                        modifier = Modifier.weight(1f),
                        label = priority.title,
                        backgroundColor = priority.color,
                        borderColor = if (priority == state.priority) {
                            Color.White
                        } else Color.Transparent,
                        labelColor = if (priority == state.priority) {
                            Color.White
                        } else Color.White.copy(alpha = 0.7f),
                        onClick = { onEvent(TaskEvent.OnPriorityChange(priority)) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            /* Bottom Sheet Mata Pelajaran */
            Text(
                text = "Mata Pelajaran",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val firstMapel = state.mapel.firstOrNull()?.name ?: ""
                Text(
                    text = state.relatedToMapel ?: firstMapel,
                    style = MaterialTheme.typography.bodyLarge
                )
                IconButton(onClick = { isBottomSheetOpen = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Pilih Mata Pelajaran",
                    )
                }
            }
            Button(
                enabled = taskTitleError == null,
                onClick = { onEvent(TaskEvent.SaveTask) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}

/* Content */

/* Top Bar */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskScreenTopBar(
    isTaskExist: Boolean,
    isComplete: Boolean,
    checkBoxBorderColor: Color,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onCheckBoxClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back"
                )
            }
        },
        title = {
            Text(
                text = "Tugas",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            if (isTaskExist) {
                TaskCheckbox(
                    isComplete = isComplete,
                    borderColor = checkBoxBorderColor,
                    onCheckBoxClick = onCheckBoxClick
                )
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Task"
                    )

                }
            }
        }
    )
}

/* Priority Button */
@Composable
private fun PriorityButton(
    modifier: Modifier = Modifier,
    label: String,
    backgroundColor: Color,
    borderColor: Color,
    labelColor: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(5.dp)
            .border(1.dp, borderColor, RoundedCornerShape(5.dp))
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = label, color = labelColor)
    }
}