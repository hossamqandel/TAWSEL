package com.hossam.tawsel.feature_profile.data.mapper

import com.hossam.tawsel.feature_profile.data.remote.dto.ProfileDto
import com.hossam.tawsel.feature_profile.domain.model.Profile

fun ProfileDto.toProfile(): Profile {
    return Profile(
        id = this.data.id,
        avatar = this.data.avatar,
        name = this.data.name,
        phone = this.data.phone,
        email = this.data.email ?: "",
        address = this.data.address
    )
}