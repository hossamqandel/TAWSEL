package com.hossam.tawsel.feature_order_details.domain.repository

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_order_details.domain.model.OrderDetails
import kotlinx.coroutines.flow.Flow

interface IOrderDetailsRepository {

    fun getOrderDetailsById(orderId: String): Flow<Resource<OrderDetailsDto>>
}