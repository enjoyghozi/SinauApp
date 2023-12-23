package com.example.sinauapp.ui.mapel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.sessions
import com.example.sinauapp.tasks
import com.example.sinauapp.ui.components.AddMapelDialog
import com.example.sinauapp.ui.components.CountCard
import com.example.sinauapp.ui.components.DeleteDialog
import com.example.sinauapp.ui.components.studySessionList
import com.example.sinauapp.ui.components.taskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapelScreen() {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val listState = rememberLazyListState()
    val isFABExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    var isEditMapelDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteMapelDialogOpen by rememberSaveable { mutableStateOf(false) }

    var mapelName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Mapel.mapelCardColor.random()) }

    AddMapelDialog(
        isOpen = isEditMapelDialogOpen,
        selectedColors = selectedColor,
        mapelName = mapelName,
        goalHours = goalHours,
        onMapelNameChange = { mapelName = it },
        onGoalHoursChange = { goalHours = it },
        onColorChange = { selectedColor = it },
        onDismissRequest = { isEditMapelDialogOpen = false },
        onConfirmButtonClick = {
            isEditMapelDialogOpen = false
        }
    )

    DeleteDialog(
        isOpen = isDeleteMapelDialogOpen,
        title = "Hapus Mapel",
        bodyText = "Apakah anda yakin ingin menghapus sesi ini? tugas dan jam belajar Anda akan dihapus secara Permanen. Tindakan ini tidak bisa dibatalkan.",
        onDismissRequest = { isDeleteMapelDialogOpen = false },
        onConfirmButtonClick = { isDeleteMapelDialogOpen = false }
    )

    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Hapus Sesi",
        bodyText = "Apakah anda yakin ingin menghapus sesi ini? jam belajar Anda akan dikurangi dengan waktu sesi ini. Tindakan ini tidak bisa dibatalkan.",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = { isDeleteSessionDialogOpen = false }
    )

    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MapelScreenTopBar(
                title = "Bahasa Indonesia",
                onBackButtonClick = {},
                onDeleteButtonClick = { isDeleteMapelDialogOpen = true },
                onEditButtonClick = { isEditMapelDialogOpen = true },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
                icon = { Icon(imageVector = Icons.Default.Add, contentDescription = "Add") },
                text = { Text(text = "Add Task") },
                expanded = isFABExpanded
            )
        }
    ) { paddingValue ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            item {
                MapelOverviewSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    studiesHours = "10",
                    goalHours = "15",
                    progress = 0.75f
                )
            }

            taskList(
                sectionTitle = "Tugas Mendatang",
                emptyListText = "Anda tidak memiliki tugas mendatang.\n" +
                        "Klik tombol + di layar subjek untuk menambahkan tugas baru.",
                tasks = tasks,
                onCheckBoxClick = {  },
                onTaskCardClick = {  }
            )

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

            taskList(
                sectionTitle = "Riwayat Tugas",
                emptyListText = "Anda tidak memiliki tugas mendatang.\n" +
                        "Klik tombol + di layar subjek untuk menambahkan tugas baru.",
                tasks = tasks,
                onCheckBoxClick = {  },
                onTaskCardClick = {  }
            )

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
            
            studySessionList(
                sectionTitle = "Waktu Belajar",
                emptyListText = "Anda tidak memiliki sesi belajar.\n" +
                        "Klik tombol + di layar mata pelajaran untuk menambahkan waktu belajar baru.",
                sessions = sessions,
                onDeleteIconClick = { isDeleteSessionDialogOpen = true }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MapelScreenTopBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onEditButtonClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate back"
                )
            }
        },
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onDeleteButtonClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Mapel"
                )
            }
            IconButton(onClick = onEditButtonClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Mapel"
                )
            }
        }
    )
}

@Composable
private fun MapelOverviewSection(
    modifier: Modifier,
    studiesHours: String,
    goalHours: String,
    progress: Float
) {
    val percentageProgress = remember(progress) {
        (progress * 100).toInt().coerceIn(0, 100)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Total Waktu Belajar",
            count = goalHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Jam Belajar",
            count = studiesHours
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.size(75.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp,
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                progress = progress,
                strokeWidth = 4.dp
            )
            Text(text = "${percentageProgress}%")
        }
    }
}
