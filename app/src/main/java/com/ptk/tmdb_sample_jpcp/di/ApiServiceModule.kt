package com.ptk.tmdb_sample_jpcp.di

import com.ptk.tmdb_sample_jpcp.network.ApiService
import com.ptk.tmdb_sample_jpcp.network.ApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideApiService(apiService: ApiServiceImpl): ApiService = apiService

}

