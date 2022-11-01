package com.hossam.tawsel.feature_profile.domain.model

data class UpdatePassword(
    val password: String,
    val password_confirmation: String
)
