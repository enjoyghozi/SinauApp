package com.example.sinauapp.domain.repository

import com.example.sinauapp.domain.model.Mapel
import kotlinx.coroutines.flow.Flow

interface MapelRepository {

    suspend fun upsertMapel(mapel: Mapel)

    fun getTotalMapelCount(): Flow<Int>

    fun getTotalGoalHours(): Flow<Float>

    suspend fun deleteMapel(mapelId: Int)

    suspend fun getMapelById(mapelId: Int): Mapel?

    fun getAllMapel(): Flow<List<Mapel>>
}