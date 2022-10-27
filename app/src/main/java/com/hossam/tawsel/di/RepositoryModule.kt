package com.hossam.tawsel.di

import com.hossam.tawsel.feature_auth.data.repository.AuthRepositoryImpl
import com.hossam.tawsel.feature_auth.domain.repository.IAuthRepository
import com.hossam.tawsel.feature_main.data.remote.ITawselService
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
    fun provideAuthRepo(api: ITawselService): IAuthRepository {
        return AuthRepositoryImpl(api)
    }

}