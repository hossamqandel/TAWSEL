package com.hossam.tawsel.feature_profile.domain.repository

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_profile.data.remote.dto.ProfileDto
import com.hossam.tawsel.feature_profile.domain.model.Profile
import com.hossam.tawsel.feature_profile.domain.model.UpdatePassword
import com.hossam.tawsel.feature_profile.domain.model.UpdateProfile
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface IProfileRepository {

    fun getProfile(phone: String, password: String): Flow<Resource<Profile>>
    fun updateProfile(updateProfile: UpdateProfile): Flow<Resource<Profile>>
    fun updatePassword(updatePassword: UpdatePassword):  Flow<Resource<Any>>
}