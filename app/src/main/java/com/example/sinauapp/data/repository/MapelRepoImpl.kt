package com.example.sinauapp.data.repository

import com.example.sinauapp.data.local.MapelDao
import com.example.sinauapp.data.local.SessionDao
import com.example.sinauapp.data.local.TaskDao
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.repository.MapelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapelRepoImpl @Inject constructor(
    private val mapelDao: MapelDao,
    private val taskDao: TaskDao,
    private val sessionDao: SessionDao
) : MapelRepository {
    override suspend fun upsertMapel(mapel: Mapel) {
        mapelDao.upsertMapel(mapel)
    }

    override fun getTotalMapelCount(): Flow<Int> {
        return mapelDao.getTotalMapelCount()
    }

    override fun getTotalGoalHours(): Flow<Float> {
        return mapelDao.getTotalGoalHours()
    }

    override suspend fun deleteMapel(mapelId: Int) {
        taskDao.deleteTasksByMapelId(mapelId)
        sessionDao.deleteSessionsByMapelId(mapelId)
        mapelDao.deleteMapel(mapelId)
    }

    override suspend fun getMapelById(mapelId: Int): Mapel? {
        return mapelDao.getMapelById(mapelId)
    }

    override fun getAllMapel(): Flow<List<Mapel>> {
        return mapelDao.getAllMapel()
    }
}