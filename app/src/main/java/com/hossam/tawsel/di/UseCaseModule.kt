package com.hossam.tawsel.di

import com.hossam.tawsel.feature_auth.domain.repository.IAuthRepository
import com.hossam.tawsel.feature_auth.domain.use_case.AuthUseCases
import com.hossam.tawsel.feature_auth.domain.use_case.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginUseCase(repo: IAuthRepository): LoginUseCase {
        return LoginUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideAuthUseCases(repo: IAuthRepository): AuthUseCases {
        return AuthUseCases(
            loginUseCase = LoginUseCase(repo)
        )
    }
}