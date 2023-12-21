package com.example.sinauapp.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sinauapp.R
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.mapel
import com.example.sinauapp.sessions
import com.example.sinauapp.tasks
import com.example.sinauapp.ui.components.AddMapelDialog
import com.example.sinauapp.ui.components.CountCard
import com.example.sinauapp.ui.components.DeleteDialog
import com.example.sinauapp.ui.components.MapelCard
import com.example.sinauapp.ui.components.studySessionList
import com.example.sinauapp.ui.components.taskList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    var isAddMapelDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isDeleteSessionDialogOpen by rememberSaveable { mutableStateOf(false) }

    var mapelName by remember { mutableStateOf("") }
    var goalHours by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Mapel.mapelCardColor.random()) }

    AddMapelDialog(
        isOpen = isAddMapelDialogOpen,
        selectedColors = selectedColor,
        mapelName = mapelName,
        goalHours = goalHours,
        onMapelNameChange = { mapelName = it },
        onGoalHoursChange = { goalHours = it },
        onColorChange = { selectedColor = it },
        onDismissRequest = { isAddMapelDialogOpen = false },
        onConfirmButtonClick = {
            isAddMapelDialogOpen = false
        }
    )
    
    DeleteDialog(
        isOpen = isDeleteSessionDialogOpen,
        title = "Hapus Sesi",
        bodyText = "Apakah anda yakin ingin menghapus sesi ini? jam belajar Anda akan dikurangi dengan waktu sesi ini. Tindakan ini tidak bisa dibatalkan.",
        onDismissRequest = { isDeleteSessionDialogOpen = false },
        onConfirmButtonClick = { isDeleteSessionDialogOpen = false }
    )

    Scaffold (
        topBar = { HomeScreenTopBar() },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                CountCardSection(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize(),
                    totalMapel = 5,
                    jamBelajar = "10",
                    totalJamBelajar = "20"
                )
            }

            item {
                MapelCardsSection(
                    modifier = Modifier.fillMaxWidth(),
                    mapelList = mapel,
                    onAddIconClicked = {
                        isAddMapelDialogOpen = true
                    }
                )
            }

            item {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 48.dp, vertical = 20.dp),
                ) {
                    Text(text = "Mulai Belajar")
                }
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
private fun HomeScreenTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Sinau.",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    )
}

@Composable
private fun CountCardSection(
    modifier: Modifier,
    totalMapel: Int,
    jamBelajar: String,
    totalJamBelajar: String
) {
    Spacer(modifier = Modifier.height(10.dp))
    Row {
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Total Mapel",
            count = "$totalMapel"
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Jam Belajar",
            count = jamBelajar
        )
        Spacer(modifier = Modifier.width(10.dp))
        CountCard(
            modifier = Modifier.weight(1f),
            headingText = "Total Jam Belajar",
            count = totalJamBelajar
        )
    }
}

@Composable
private fun MapelCardsSection(
    modifier: Modifier,
    mapelList: List<Mapel>,
    emptyListText: String = "Anda tidak memiliki mata pelajaran apa pun.\n Klik tombol + untuk menambahkan mata pelajaran baru.",
    onAddIconClicked: () -> Unit
) {
    Column(modifier = modifier) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mata Pelajaran",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            IconButton(onClick = onAddIconClicked) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Mapel"
                )
            }
        }
        if (mapelList.isEmpty()) {
            Image(
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.img_books),
                contentDescription = emptyListText
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = emptyListText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp)
        ) {
            items(mapelList) { mapel ->
                MapelCard(
                    mapelName = mapel.name,
                    gradientColor = mapel.colors,
                    onClick = {}
                )
            }
        }

    }
}