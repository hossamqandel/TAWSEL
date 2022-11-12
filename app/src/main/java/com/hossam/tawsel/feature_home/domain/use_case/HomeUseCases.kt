package com.hossam.tawsel.feature_home.domain.use_case

data class HomeUseCases(
    val getHomeUseCase: GetHomeUseCase,
    val startOrderUseCase: StartOrderUseCase,
    val cancelOrderUseCase: CancelOrderUseCase,
    val completeOrderUseCase: CompleteOrderUseCase

)
