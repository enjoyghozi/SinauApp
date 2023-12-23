package com.example.sinauapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.ui.home.HomeScreen
import com.example.sinauapp.ui.mapel.MapelScreen
import com.example.sinauapp.ui.session.SessionScreen
import com.example.sinauapp.ui.task.TaskScreen
import com.example.sinauapp.ui.theme.SinauAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SinauAppTheme {
//                HomeScreen()
//                MapelScreen()
//                TaskScreen()
                SessionScreen()
            }
        }
    }
}

val mapel = listOf(
    Mapel(
        name = "English",
        goalHours = 10f,
        colors = Mapel.mapelCardColor[0],
        mapelId = 0
    ),
    Mapel(
        name = "Physics",
        goalHours = 10f,
        colors = Mapel.mapelCardColor[1],
        mapelId = 0
    ),
    Mapel(
        name = "Maths",
        goalHours = 10f,
        colors = Mapel.mapelCardColor[2],
        mapelId = 0
    ),
    Mapel(
        name = "Geology",
        goalHours = 10f,
        colors = Mapel.mapelCardColor[3],
        mapelId = 0
    ),
    Mapel(
        name = "Fine Arts",
        goalHours = 10f,
        colors = Mapel.mapelCardColor[4],
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
        isStatus = false,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Do Homework",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToMapel = "",
        isStatus = true,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Go Coaching",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToMapel = "",
        isStatus = false,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Assignment",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToMapel = "",
        isStatus = false,
        taskMapelId = 0,
        taskId = 1
    ),
    Task(
        title = "Write Poem",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToMapel = "",
        isStatus = true,
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