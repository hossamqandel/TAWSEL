package com.hossam.tawsel.feature_order_details.presentation

import com.hossam.tawsel.feature_order_details.data.remote.dto.ItemDto
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_order_details.domain.model.Items
import com.hossam.tawsel.feature_order_details.domain.model.OrderDetails

data class OrderDetailsState(
    val isActive: Boolean = true,
    val paymentType: String = "",
    val currentAddress: String = "",
    val restaurantAvatar: String = "",
    val restaurantName: String = "",
    val restaurantAddress: String = "",
    val clientPhone: String = "",
    val time: String = "",
    val reason: String = "",
    val items: List<ItemDto> = emptyList(),
    val clientInfos: List<ClientInfo> = emptyList(),
    val orderDetailsDto: OrderDetailsDto? = null,
)
