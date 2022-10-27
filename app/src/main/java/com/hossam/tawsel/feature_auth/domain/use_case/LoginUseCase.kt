package com.hossam.tawsel.feature_auth.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.SharedPref
import com.hossam.tawsel.feature_auth.data.remote.dto.AuthenticationDto
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_auth.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: IAuthRepository
) {

    suspend operator fun invoke(login: Login): Flow<Resource<AuthenticationDto>> {
        val phone = login.phone
        val password = login.password

        if (phone.isNullOrBlank()) {
            return flow { emit(Resource.Error("Phone field can't be blank")) }
        }

        if (password.isNullOrBlank()) {
            return flow { emit(Resource.Error("Password field can't be blank")) }
        }

        if (phone.length != 12) {
            return flow { emit(Resource.Error("Phone numbers must be 11 numbers")) }
        }

        if (password.length < 8) {
            return flow { emit(Resource.Error("Password should be at least contain 8 chars")) }
        }

        return repo.login(login).map { resource ->
            when (resource) {
                is Resource.Loading -> Resource.Loading()
                is Resource.Success -> {
                    if (resource.data?.error != null) {
                        Resource.Error("${resource.data.error}")
                    } else if (resource.data?.message != null) {
                        Resource.Error("${resource.data.message}")
                    } else {
                        SharedPref.setUserPhone(phone.toString())
                        SharedPref.setUserToken(resource.data?.toToken().toString())
                        Resource.Success(resource.data!!)
                    }
                }
                is Resource.Error -> {
                    Resource.Error(resource.message.toString())
                }
            }
        }
    }
}