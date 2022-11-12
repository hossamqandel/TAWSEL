package com.hossam.tawsel.feature_notification.domain.repository

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDto
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface INotificationRepository {

    fun getAllNotifications(): Flow<Resource<NotificationsDto>>
}