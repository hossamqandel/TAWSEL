package com.hossam.tawsel.di

import com.hossam.tawsel.feature_auth.data.repository.AuthRepositoryImpl
import com.hossam.tawsel.feature_auth.domain.repository.IAuthRepository
import com.hossam.tawsel.feature_home.data.repository.HomeRepositoryImpl
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import com.hossam.tawsel.feature_notification.data.repository.NotificationRepositoryImpl
import com.hossam.tawsel.feature_notification.domain.repository.INotificationRepository
import com.hossam.tawsel.feature_order_details.data.repository.OrderDetailsRepositoryImpl
import com.hossam.tawsel.feature_order_details.domain.repository.IOrderDetailsRepository
import com.hossam.tawsel.feature_orders.data.repository.OrdersRepositoryImpl
import com.hossam.tawsel.feature_orders.domain.repository.IOrdersRepository
import com.hossam.tawsel.feature_profile.data.repository.ProfileRepositoryImpl
import com.hossam.tawsel.feature_profile.domain.repository.IProfileRepository
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

    @Provides
    @Singleton
    fun provideHomeRepository(api: ITawselService): IHomeRepository {
        return HomeRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(api: ITawselService): IProfileRepository {
        return ProfileRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOrderDetailsRepository(api: ITawselService): IOrderDetailsRepository {
        return OrderDetailsRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(api: ITawselService): IOrdersRepository {
        return OrdersRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideNotificationRepository(api: ITawselService): INotificationRepository {
        return NotificationRepositoryImpl(api)
    }
}