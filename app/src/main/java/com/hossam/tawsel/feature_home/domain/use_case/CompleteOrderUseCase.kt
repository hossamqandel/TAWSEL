package com.hossam.tawsel.feature_home.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompleteOrderUseCase @Inject constructor(
    private val repo: IHomeRepository
) {

    operator fun invoke(orderId: String): Flow<Resource<Boolean>> {
        return repo.completeOrder(orderId = orderId)
    }
}