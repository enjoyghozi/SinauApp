package com.example.sinauapp.di

import android.app.Application
import androidx.room.Room
import com.example.sinauapp.data.local.AppDatabase
import com.example.sinauapp.data.local.MapelDao
import com.example.sinauapp.data.local.SessionDao
import com.example.sinauapp.data.local.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(
                application,
                AppDatabase::class.java,
                "sinauapp.db"
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideMapelDao(database: AppDatabase): MapelDao {
        return database.mapelDao()
    }

    @Provides
    @Singleton
    fun provideTaskDaoDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideSessionDao(database: AppDatabase): SessionDao {
        return database.sessionDao()
    }
}