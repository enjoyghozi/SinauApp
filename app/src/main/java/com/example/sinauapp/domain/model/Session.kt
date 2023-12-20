package com.example.sinauapp.domain.model

import java.time.Duration

data class Session(
    val sessionMapelId: Int,
    val relatedToMapel: String,
    val date: Long,
    val duration: Long,
    val sessionId: Int
)
