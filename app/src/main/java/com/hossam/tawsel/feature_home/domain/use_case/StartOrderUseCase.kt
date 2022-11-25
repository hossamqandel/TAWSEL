package com.hossam.tawsel.feature_home.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import com.hossam.tawsel.feature_home.presentation.OrderEvent
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StartOrderUseCase (
    private val repo: IHomeRepository
) {

    operator fun invoke(orderId: String): Flow<Resource<Boolean>> {
        return repo.startOrder(orderId = orderId)
    }

}