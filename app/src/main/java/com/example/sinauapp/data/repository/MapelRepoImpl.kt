package com.example.sinauapp.data.repository

import com.example.sinauapp.data.local.MapelDao
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.repository.MapelRepository
import kotlinx.coroutines.flow.Flow

class MapelRepoImpl(
    private val mapelDao: MapelDao
) : MapelRepository {
    override suspend fun upsertMapel(mapel: Mapel) {
        mapelDao.upsertMapel(mapel)
    }

    override fun getTotalMapelCount(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override fun getTotalGoalHours(): Flow<Float> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMapel(mapelInt: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getMapelById(mapelInt: Int): Mapel? {
        TODO("Not yet implemented")
    }

    override fun getAllMapels(): Flow<List<Mapel>> {
        TODO("Not yet implemented")
    }
}