package com.example.sinauapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sinauapp.domain.model.Mapel
import com.example.sinauapp.domain.model.Session
import com.example.sinauapp.domain.model.Task

@Database(
    entities = [Mapel::class, Session::class, Task::class],
    version = 1
)
@TypeConverters(ColorListConverter::class)
abstract class Database: RoomDatabase() {

    abstract fun subjectDao(): MapelDao
    abstract fun taskDao(): TaskDao
    abstract fun sessionDao(): SessionDao
}