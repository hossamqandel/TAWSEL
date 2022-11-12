package com.hossam.tawsel.feature_home.data.remote.dto

data class HomeDto(
    val count: Int,
    val current: CurrentDto,
    val wallet: Int,
    val error: String? = null,
    val message: String? = null,
)

data class CurrentDto(
    val address: Any,
    val address_details: String,
    val city_id: Any,
    val client: String,
    val company_id: Int,
    val created_at: String,
    val date: String,
    val driver_id: Int,
    val end: String,
    val id: Int,
    val other_phone: String,
    val paid: Int,
    val payment_type: String,
    val phone: String,
    val reason: Any,
    val shipping: String,
    val start: String,
    val status: String,
    val total: String,
    val updated_at: String
)