package com.hossam.tawsel.feature_notification.data.remote.dto

data class NotificationsDto(
    val `data`: List<NotificationsDataDto>
)

data class NotificationsDataDto(
    val body: String,
    val id: String,
    val read_at: String,
    val status: String,
    val title: String
)