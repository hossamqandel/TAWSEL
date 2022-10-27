package com.hossam.tawsel.feature_orders.domain.model

data class OrdersDto(
    val `data`: List<DataDto>
)

data class DataDto(
    val address: String,
    val address_details: String,
    val city: Any,
    val client: String,
    val created_at: String,
    val date: String,
    val end: String,
    val id: Int,
    val items: List<ItemDto>,
    val other_phone: String,
    val phone: String,
    val reasons: Any,
    val shipping : String,
    val start: String,
    val status: String,
    val total: String
)

data class ItemDto(
    val desc: Any,
    val id: Int,
    val item: String,
    val order_id: Int,
    val price: String,
    val quantity: String,
    val total: String
)