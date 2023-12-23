package com.example.sinauapp.ui.session

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sinauapp.mapel
import com.example.sinauapp.sessions
import com.example.sinauapp.ui.components.DeleteDialog
import com.example.sinauapp.ui.components.MapelListBottomSheet
import com.example.sinauapp.ui.components.studySessionList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen() {

    val scope = rememberCoroutineScope()

    val sheetState = rememberModalBottomSheetState()
    var isBottomSheetOpen by remember { mutableStateOf(false) }

    var isDeleteDialogOpen by remember { mutableStateOf(false) }

    MapelListBottomSheet(
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        mapel = mapel,
        onDismissRequest = { isBottomSheetOpen = false },
        onMapelClicked = {
            isBottomSheetOpen = false
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) isBottomSheetOpen = false
            }
        }
    )

    DeleteDialog(
        isOpen = isDeleteDialogOpen,
        title = "Hapus Sesi?",
        bodyText = "Apakah anda yakin ingin menghapus sesi ini?" + "\n" + "Sesi yang di hapus tidak dapat dikembalikan",
        onDismissRequest = { isDeleteDialogOpen = false },
        onConfirmButtonClick = {
            isDeleteDialogOpen = false
        }
    )

    Scaffold(
        topBar = {
            sessionScreenTopBar(onBackButtonClicked = {})
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            item {
                timerSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }

            item {
                relatedToMapelSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    relatedToMapel = "Matematika",
                    selectMapelButtonClicked = {
                        isBottomSheetOpen = true
                    }
                )
            }

            item {
                timerButtonSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    startButtonClick = { /*TODO*/ },
                    cancelButtonClick = { /*TODO*/ },
                    finishButtonClick = { /*TODO*/ }
                )
            }

            studySessionList(
                sectionTitle = "Waktu Belajar",
                emptyListText = "Anda tidak memiliki sesi belajar.\n" +
                        "Klik tombol + di layar mata pelajaran untuk menambahkan waktu belajar baru.",
                sessions = sessions,
                onDeleteIconClick = {
                    isDeleteDialogOpen = true
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun sessionScreenTopBar(
    onBackButtonClicked: () -> Unit
) {
    TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Navigate back"
                    )
                }
            },
            title = {
                Text(
                    text = "Sesi Belajar",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
    )
}

@Composable
private fun timerSection(
    modifier: Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .border(5.dp, MaterialTheme.colorScheme.surfaceVariant, CircleShape)
        )
        Text(
            text = "00:05:32",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 45.sp)
        )
    }
}

@Composable
private fun relatedToMapelSection(
    modifier: Modifier,
    relatedToMapel: String,
    selectMapelButtonClicked: () -> Unit
) {
    Column (modifier = modifier) {
        Text(
            text = "Mata Pelajaran",
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "English",
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = selectMapelButtonClicked) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Subject"
                )
            }
        }
    }
}

@Composable
private fun timerButtonSection(
    modifier: Modifier,
    startButtonClick: () -> Unit,
    cancelButtonClick: () -> Unit,
    finishButtonClick: () -> Unit
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = cancelButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Batalkan"
            )
        }

        Button(onClick = startButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Mulai"
            )
        }

        Button(onClick = finishButtonClick) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = "Selesai"
            )
        }
    }
}