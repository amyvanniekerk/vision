package com.example.vision.di

import com.example.vision.data.repository.AuthRepository
import com.example.vision.data.repository.EyeCareRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepository()
    }
    
    @Provides
    @Singleton
    fun provideEyeCareRepository(): EyeCareRepository {
        return EyeCareRepository()
    }
}