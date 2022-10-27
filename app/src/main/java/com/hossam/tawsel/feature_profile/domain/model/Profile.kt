package com.hossam.tawsel.feature_profile.domain.model

data class Profile(
    val id: Int,
    val avatar: String,
    val name: String,
    val phone: String,
    val email: String,
    val address: String,
)
