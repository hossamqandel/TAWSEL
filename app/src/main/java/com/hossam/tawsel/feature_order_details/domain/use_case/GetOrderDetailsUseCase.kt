package com.hossam.tawsel.feature_order_details.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_order_details.domain.model.OrderDetails
import com.hossam.tawsel.feature_order_details.domain.repository.IOrderDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(
    private val repo: IOrderDetailsRepository
) {

    operator fun invoke(orderId: String): Flow<Resource<OrderDetailsDto>> {
        return if (orderId.isNullOrBlank()){
            flow { emit(Resource.Error("An unexpected error.. please contact the developer ")) }
        } else {
            repo.getOrderDetailsById(orderId)
        }
    }
}