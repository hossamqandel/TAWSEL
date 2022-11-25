package com.hossam.tawsel.feature_notification.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDto
import com.hossam.tawsel.feature_notification.domain.repository.INotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotificationsUseCase (
    private val repo: INotificationRepository
) {

    operator fun invoke(): Flow<Resource<NotificationsDto>> = repo.getAllNotifications()
}