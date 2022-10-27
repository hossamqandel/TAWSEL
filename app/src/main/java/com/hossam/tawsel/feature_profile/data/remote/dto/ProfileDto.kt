package com.hossam.tawsel.feature_profile.data.remote.dto

data class ProfileDto(
    val `data`: Data
)

data class Data(
    val address: String,
    val avatar: String,
    val created_at: String,
    val email: String? = null,
    val id: Int,
    val name: String,
    val phone: String,
    val status: Int,
    val wallet: Int
)