package com.example.sinauapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.sinauapp.ui.theme.SinauAppTheme
import com.example.sinauapp.ui.home.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SinauAppTheme {
                HomeScreen()
            }
        }
    }
}
