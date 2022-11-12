package com.hossam.tawsel.feature_home.domain.model

import com.hossam.tawsel.feature_home.data.remote.dto.CurrentDto

data class Home(
    val count: Int = 0,
    val current: CurrentDto,
    val wallet: Int = 0
)

data class Current(
    val driverId: Int,
    val paymentType: String,
    val phone: String,
    val reason: Any
)

// TODO This Model didn't completed yet