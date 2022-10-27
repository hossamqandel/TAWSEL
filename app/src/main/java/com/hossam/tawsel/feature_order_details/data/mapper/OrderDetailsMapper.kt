package com.hossam.tawsel.feature_order_details.data.mapper

import com.hossam.tawsel.feature_order_details.data.remote.dto.ItemDto
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_order_details.domain.model.Item
import com.hossam.tawsel.feature_order_details.domain.model.Items
import com.hossam.tawsel.feature_order_details.domain.model.OrderDetails

fun OrderDetailsDto.toOrderDetails(): OrderDetails {
    return OrderDetails(
        id = this.data.id,
        client = this.data.client,
        phone = this.data.phone,
        addressDetails = this.data.address_details,
        shippingTax = this.data.shipping,
        items = this.data.items.toItems()
    )
}

fun Collection<ItemDto>.toItems(): Items {
    val items = mutableListOf<Item>()
    this.map {
        items.add(Item(
            id = it.id,
            orderId = it.order_id,
            item = it.item,
            price = it.price,
            quantity = it.quantity,
            total = it.total
        ))
    }
    return items
}