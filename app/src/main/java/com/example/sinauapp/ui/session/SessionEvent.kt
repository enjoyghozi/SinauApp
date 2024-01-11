package com.example.sinauapp.ui.session

import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session

sealed class SessionEvent {
    data class OnRelatedToMapelChange(val mapel: Mapel) : SessionEvent()
    data class SaveSession(val duration: Long)  : SessionEvent()
    data class OnDeleteSessionButtonClick(val session: Session) : SessionEvent()
    data object DeleteSession : SessionEvent()
    data object NotifyToUpdateMapel : SessionEvent()
    data class UpdateMapelIdAndRelatedToMapel(
        val mapelId: Int?,
        val relatedToMapel: String?
    ) : SessionEvent()
}
