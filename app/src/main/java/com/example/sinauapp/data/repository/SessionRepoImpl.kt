package com.example.sinauapp.data.repository

import com.example.sinauapp.data.local.SessionDao
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SessionRepoImpl @Inject constructor(
    private val sessionDao: SessionDao
): SessionRepository {

    override suspend fun insertSession(session: Session) {
        sessionDao.insertSession(session)
    }

    override suspend fun deleteSession(session: Session) {
        sessionDao.deleteSession(session)
    }

    override fun getAllSessions(): Flow<List<Session>> {
        return sessionDao.getAllSessions()
    }

    override fun getRecentFiveSessions(): Flow<List<Session>> {
        return sessionDao.getAllSessions().take(count = 5)
    }

    override fun getRecentTenSessionsForMapel(mapelId: Int): Flow<List<Session>> {
        return sessionDao.getRecentSessionsForMapel(mapelId).take(count = 10)
    }

    override fun getTotalSessionsDuration(): Flow<Long> {
        return sessionDao.getTotalSessionsDuration()
    }

    override fun getTotalSessionsDurationByMapel(mapelId: Int): Flow<Long> {
        return sessionDao.getTotalSessionsDurationByMapel(mapelId)
    }
}