package com.hossam.tawsel.feature_auth.data.remote.dto

data class AuthenticationDto(
    val access_token: String? = null,
    val expires_in: Int? = null,
    val token_type: String? = null,
    val error: String? = null,
    val message: String? = null
){

    fun toToken(): String {
        return access_token?.let {
            return it
        } ?: ""
    }
}