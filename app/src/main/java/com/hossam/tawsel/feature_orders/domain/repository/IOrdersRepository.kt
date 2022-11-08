package com.hossam.tawsel.feature_orders.domain.repository

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_orders.domain.model.DataDto
import com.hossam.tawsel.feature_orders.domain.model.OrdersDto
import kotlinx.coroutines.flow.Flow

interface IOrdersRepository {

    suspend fun getOrders(): OrdersDto
}