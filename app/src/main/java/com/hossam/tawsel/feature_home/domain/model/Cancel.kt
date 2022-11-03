package com.hossam.tawsel.feature_home.domain.model

data class Cancel(
    val orderId: Int,
    val reason: String = ""
)
