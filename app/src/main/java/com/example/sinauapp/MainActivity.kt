package com.example.sinauapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.ui.NavGraphs
import com.example.sinauapp.ui.theme.SinauAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SinauAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
        requestPermissions()
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }
    }
}

val mapel = listOf  (
    Mapel(
        name = "English",
        goalHours = 10f,
        colors = Mapel.mapelCardColors[0].map { it.toArgb() },
        mapelId = 0
    ),
    Mapel(
        name = "Physics",
        goalHours = 10f,
        colors = Mapel.mapelCardColors[1].map { it.toArgb() },
        mapelId = 0
    ),
    Mapel(
        name = "Maths",
        goalHours = 10f,
        colors = Mapel.mapelCardColors[2].map { it.toArgb() },
        mapelId = 0
    ),
    Mapel(
        name = "Geology",
        goalHours = 10f,
        colors = Mapel.mapelCardColors[3].map { it.toArgb() },
        mapelId = 0
    ),
    Mapel(
        name = "Fine Arts",
        goalHours = 10f,
        colors = Mapel.mapelCardColors[4].map { it.toArgb() },
        mapelId = 0
    ),
)

val tasks = listOf(
    Task(
        title = "Prepare notes",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToMapel = "",
        isComplete = false,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Do Homework",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToMapel = "",
        isComplete = true,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Go Coaching",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToMapel = "",
        isComplete = false,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Assignment",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToMapel = "",
        isComplete = false,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Write Poem",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToMapel = "",
        isComplete = true,
        taskMapelId = 0,
        taskId = 1
    )
)

val sessions = listOf(
    Session(
        relatedToMapel = "English",
        date = 0L,
        duration = 2,
        sessionMapelId = 0,
        sessionId = 0
    ),
    Session(
        relatedToMapel = "English",
        date = 0L,
        duration = 2,
        sessionMapelId = 0,
        sessionId = 0
    ),
    Session(
        relatedToMapel = "Physics",
        date = 0L,
        duration = 2,
        sessionMapelId = 0,
        sessionId = 0
    ),
    Session(
        relatedToMapel = "Maths",
        date = 0L,
        duration = 2,
        sessionMapelId = 0,
        sessionId = 0
    ),
    Session(
        relatedToMapel = "English",
        date = 0L,
        duration = 2,
        sessionMapelId = 0,
        sessionId = 0
    )
)