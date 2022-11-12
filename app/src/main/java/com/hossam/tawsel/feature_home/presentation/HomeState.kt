package com.hossam.tawsel.feature_home.presentation

data class HomeState(
    val driverAvatar: String = "",
    val driverName: String = "",
    val currentLocation: String = "",
    val isOnline: Boolean = false,
    val hasOrder: Boolean = false,
    val paymentType: String = "",
    val time: String = "",
    val restaurantAvatar: String = "",
    val restaurantName: String = "",
    val ordersCount: Int = 0,
    val walletAmount: Int = 0,
)
