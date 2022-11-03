package com.hossam.tawsel.feature_order_details.domain.use_case

import com.hossam.tawsel.feature_home.domain.use_case.CancelOrderUseCase
import com.hossam.tawsel.feature_home.domain.use_case.CompleteOrderUseCase

data class OrderDetailsUseCases(
    val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    val completeOrderUseCase: CompleteOrderUseCase,
    val cancelOrderUseCase: CancelOrderUseCase
)
