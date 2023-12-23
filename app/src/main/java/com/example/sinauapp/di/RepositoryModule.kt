package com.example.sinauapp.di

import com.example.sinauapp.data.repository.MapelRepoImpl
import com.example.sinauapp.data.repository.SessionRepoImpl
import com.example.sinauapp.data.repository.TaskRepoImpl
import com.example.sinauapp.domain.repository.MapelRepository
import com.example.sinauapp.domain.repository.SessionRepository
import com.example.sinauapp.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMapelRepository(
        impl: MapelRepoImpl
    ): MapelRepository

    @Singleton
    @Binds
    abstract fun bindTaskRepository(
        impl: TaskRepoImpl
    ): TaskRepository

    @Singleton
    @Binds
    abstract fun bindSessionRepository(
        impl: SessionRepoImpl
    ): SessionRepository
}