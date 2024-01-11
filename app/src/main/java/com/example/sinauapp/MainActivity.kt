package com.example.sinauapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task
import com.example.sinauapp.ui.NavGraphs
import com.example.sinauapp.ui.destinations.SessionScreenRouteDestination
import com.example.sinauapp.ui.session.SessionScreenRoute
import com.example.sinauapp.ui.session.SessionTimerService
import com.example.sinauapp.ui.theme.SinauAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.dependency
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var isBound by mutableStateOf(false)
    private lateinit var timerService: SessionTimerService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, service: IBinder?) {
            val binder = service as SessionTimerService.SessionTimerBinder
            timerService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound =  false
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, SessionTimerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isBound) {
                SinauAppTheme {
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        dependenciesContainerBuilder = {
                            dependency(SessionScreenRouteDestination) { timerService }
                        }
                    )
                }
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

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        isBound = false
    }
}

//val mapel = listOf  (
//    Mapel(
//        name = "English",
//        goalHours = 10f,
//        colors = Mapel.mapelCardColors[0].map { it.toArgb() },
//        mapelId = 0
//    ),
//    Mapel(
//        name = "Physics",
//        goalHours = 10f,
//        colors = Mapel.mapelCardColors[1].map { it.toArgb() },
//        mapelId = 0
//    ),
//    Mapel(
//        name = "Maths",
//        goalHours = 10f,
//        colors = Mapel.mapelCardColors[2].map { it.toArgb() },
//        mapelId = 0
//    ),
//    Mapel(
//        name = "Geology",
//        goalHours = 10f,
//        colors = Mapel.mapelCardColors[3].map { it.toArgb() },
//        mapelId = 0
//    ),
//    Mapel(
//        name = "Fine Arts",
//        goalHours = 10f,
//        colors = Mapel.mapelCardColors[4].map { it.toArgb() },
//        mapelId = 0
//    ),
//)
//
//val tasks = listOf(
//    Task(
//        title = "Prepare notes",
//        description = "",
//        dueDate = 0L,
//        priority = 0,
//        relatedToMapel = "",
//        isComplete = false,
//        taskMapelId = 0,
//        taskId = 1
//    ),
//    Task(
//        title = "Do Homework",
//        description = "",
//        dueDate = 0L,
//        priority = 1,
//        relatedToMapel = "",
//        isComplete = true,
//        taskMapelId = 0,
//        taskId = 1
//    ),
//    Task(
//        title = "Go Coaching",
//        description = "",
//        dueDate = 0L,
//        priority = 2,
//        relatedToMapel = "",
//        isComplete = false,
//        taskMapelId = 0,
//        taskId = 1
//    ),
//    Task(
//        title = "Assignment",
//        description = "",
//        dueDate = 0L,
//        priority = 1,
//        relatedToMapel = "",
//        isComplete = false,
//        taskMapelId = 0,
//        taskId = 1
//    ),
//    Task(
//        title = "Write Poem",
//        description = "",
//        dueDate = 0L,
//        priority = 0,
//        relatedToMapel = "",
//        isComplete = true,
//        taskMapelId = 0,
//        taskId = 1
//    )
//)
//
//val sessions = listOf(
//    Session(
//        relatedToMapel = "English",
//        date = 0L,
//        duration = 2,
//        sessionMapelId = 0,
//        sessionId = 0
//    ),
//    Session(
//        relatedToMapel = "English",
//        date = 0L,
//        duration = 2,
//        sessionMapelId = 0,
//        sessionId = 0
//    ),
//    Session(
//        relatedToMapel = "Physics",
//        date = 0L,
//        duration = 2,
//        sessionMapelId = 0,
//        sessionId = 0
//    ),
//    Session(
//        relatedToMapel = "Maths",
//        date = 0L,
//        duration = 2,
//        sessionMapelId = 0,
//        sessionId = 0
//    ),
//    Session(
//        relatedToMapel = "English",
//        date = 0L,
//        duration = 2,
//        sessionMapelId = 0,
//        sessionId = 0
//    )
//)