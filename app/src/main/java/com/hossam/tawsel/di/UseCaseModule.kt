package com.hossam.tawsel.di

import com.hossam.tawsel.feature_auth.domain.repository.IAuthRepository
import com.hossam.tawsel.feature_auth.domain.use_case.AuthUseCases
import com.hossam.tawsel.feature_auth.domain.use_case.LoginUseCase
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import com.hossam.tawsel.feature_home.domain.use_case.*
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import com.hossam.tawsel.feature_notification.domain.repository.INotificationRepository
import com.hossam.tawsel.feature_notification.domain.use_case.GetNotificationsUseCase
import com.hossam.tawsel.feature_notification.domain.use_case.NotificationUseCases
import com.hossam.tawsel.feature_order_details.domain.repository.IOrderDetailsRepository
import com.hossam.tawsel.feature_order_details.domain.use_case.GetOrderDetailsUseCase
import com.hossam.tawsel.feature_order_details.domain.use_case.OrderDetailsUseCases
import com.hossam.tawsel.feature_orders.domain.repository.IOrdersRepository
import com.hossam.tawsel.feature_orders.domain.use_case.GetOrdersUseCase
import com.hossam.tawsel.feature_profile.domain.repository.IProfileRepository
import com.hossam.tawsel.feature_profile.domain.use_case.GetProfileUseCase
import com.hossam.tawsel.feature_profile.domain.use_case.ProfileUseCases
import com.hossam.tawsel.feature_profile.domain.use_case.UpdatePasswordUseCase
import com.hossam.tawsel.feature_profile.domain.use_case.UpdateProfileUseCase
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


    @Provides
    @Singleton
    fun provideGetHomeUseCase(repo: IHomeRepository): GetHomeUseCase {
        return GetHomeUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideStartOrderUseCase(repo: IHomeRepository): StartOrderUseCase {
        return StartOrderUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideCancelOrderUseCase(repo: IHomeRepository): CancelOrderUseCase {
        return CancelOrderUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideCompleteOrderUseCase(repo: IHomeRepository): CompleteOrderUseCase {
        return CompleteOrderUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideHomeUseCases(repo: IHomeRepository): HomeUseCases {
        return HomeUseCases(
            getHomeUseCase = GetHomeUseCase(repo),
            startOrderUseCase = StartOrderUseCase(repo),
            cancelOrderUseCase = CancelOrderUseCase(repo),
            completeOrderUseCase = CompleteOrderUseCase(repo)
        )
    }

    @Provides
    @Singleton
    fun provideProfileUseCases(repo: IProfileRepository): ProfileUseCases {
        return ProfileUseCases(
            getProfileUseCase = GetProfileUseCase(repo),
            updateProfileUseCase = UpdateProfileUseCase(repo),
            updatePasswordUseCase = UpdatePasswordUseCase(repo)
        )
    }

    @Provides
    @Singleton
    fun provideGetProfileUseCase(repo: IProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(repo: IProfileRepository): UpdateProfileUseCase {
        return UpdateProfileUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideUpdatePasswordUseCase(repo: IProfileRepository): UpdatePasswordUseCase {
        return UpdatePasswordUseCase(repo)
    }


    @Provides
    @Singleton
    fun provideOrderDetailsUseCases(repo: IOrderDetailsRepository, homeRepo: IHomeRepository): OrderDetailsUseCases {
        return OrderDetailsUseCases(
            getOrderDetailsUseCase = GetOrderDetailsUseCase(repo),
            completeOrderUseCase = CompleteOrderUseCase(homeRepo),
            cancelOrderUseCase = CancelOrderUseCase(homeRepo)
        )
    }

    @Provides
    @Singleton
    fun provideGetOrderDetailsUseCase(repo: IOrderDetailsRepository): GetOrderDetailsUseCase {
        return GetOrderDetailsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetOrdersUseCase(repo: IOrdersRepository): GetOrdersUseCase {
        return GetOrdersUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideNotificationsUseCase(repo: INotificationRepository): GetNotificationsUseCase {
        return GetNotificationsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideNotificationUseCases(repo: INotificationRepository): NotificationUseCases {
        return NotificationUseCases(
            GetNotificationsUseCase(repo)
        )
    }
}