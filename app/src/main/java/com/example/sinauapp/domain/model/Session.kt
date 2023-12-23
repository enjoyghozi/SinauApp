package com.example.sinauapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration

@Entity
data class Session(
    val sessionMapelId: Int,
    val relatedToMapel: String,
    val date: Long,
    val duration: Long,
    @PrimaryKey(autoGenerate = true)
    val sessionId: Int? = null
)
