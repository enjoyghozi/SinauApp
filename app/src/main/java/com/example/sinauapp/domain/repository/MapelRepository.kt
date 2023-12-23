package com.example.sinauapp.domain.repository

import com.example.sinauapp.domain.model.Mapel
import kotlinx.coroutines.flow.Flow

interface MapelRepository {

    suspend fun upsertMapel(mapel: Mapel)

    fun getTotalMapelCount(): Flow<Int>

    fun getTotalGoalHours(): Flow<Float>

    suspend fun deleteMapel(mapelInt: Int)

    suspend fun getMapelById(mapelInt: Int): Mapel?

    fun getAllMapel(): Flow<List<Mapel>>
}