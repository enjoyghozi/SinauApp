package com.example.sinauapp.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.sinauapp.domain.model.Mapel
import kotlinx.coroutines.flow.Flow

@Dao
interface MapelDao {
    @Upsert
    suspend fun upsertMapel(mapel: Mapel)

    @Query("SELECT COUNT(*) FROM MAPEL")
    fun getTotalMapelCount(): Flow<Int>

    @Query("SELECT SUM(goalHours) FROM MAPEL")
    fun getTotalGoalHours(): Flow<Float>

    @Query("SELECT * FROM MAPEL WHERE mapelId = :mapelId")
    suspend fun getMapelById(mapelId: Int): Mapel?

    @Query("DELETE FROM MAPEL WHERE mapelId = :mapelId")
    suspend fun deleteMapel(mapelId: Int)

    @Query("SELECT * FROM MAPEL")
    fun getAllMapel(): Flow<List<Mapel>>

}