package com.hossam.tawsel.feature_auth.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.hossam.tawsel.core.Const
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_auth.data.remote.dto.AuthenticationDto
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_auth.domain.repository.IAuthRepository
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: ITawselService
): IAuthRepository {

    override fun login(login: Login): Flow<Resource<AuthenticationDto>> = flow {

        try {
            emit(Resource.Loading())
            val mAuthData = api.login(login)
            emit(Resource.Success(mAuthData))
        } catch (e: IOException){
            Log.i(TAG, "login: $e")
            emit(Resource.Error(Const.EXP_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "login: $e")
            emit(Resource.Error(Const.EXP_MESSAGE_IO))
        }
    }
}