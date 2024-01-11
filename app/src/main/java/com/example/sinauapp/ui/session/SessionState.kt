package com.example.sinauapp.ui.session

import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session

data class SessionState(
    val mapel: List<Mapel> = emptyList(),
    val sessions: List<Session> = emptyList(),
    val relatedToMapel: String? = null,
    val mapelId: Int? = null,
    val session: Session? = null
)
