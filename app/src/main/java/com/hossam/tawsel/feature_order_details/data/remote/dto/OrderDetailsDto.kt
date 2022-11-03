package com.hossam.tawsel.feature_order_details.data.remote.dto

//client(name, phone, paymentType, address)
//order(item, price, total, shipping, created at, address details)
data class OrderDetailsDto(
    val `data`: DataDto
)

data class DataDto(
    val address: Any,
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