package com.hossam.tawsel.feature_auth.domain.repository

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_auth.data.remote.dto.AuthenticationDto
import com.hossam.tawsel.feature_auth.domain.model.Login
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    fun login(login: Login): Flow<Resource<AuthenticationDto>>
}