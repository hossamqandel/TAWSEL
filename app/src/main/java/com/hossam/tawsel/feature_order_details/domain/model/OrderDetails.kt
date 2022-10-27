package com.hossam.tawsel.feature_order_details.domain.model

import com.hossam.tawsel.feature_orders.domain.model.ItemDto

data class OrderDetails(
    val id: Int,
    val client: String,
    val phone: String,
    val addressDetails: String,
    val shippingTax: String,
    val items: List<Item>
)

typealias Items = List<Item>

data class Item(
    val id: Int,
    val orderId: Int,
    val item: String,
    val price: String,
    val quantity: String,
    val total: String,
)
