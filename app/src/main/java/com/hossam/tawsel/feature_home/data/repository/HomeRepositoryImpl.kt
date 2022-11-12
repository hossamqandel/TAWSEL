package com.hossam.tawsel.feature_home.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.hossam.tawsel.core.Const
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_home.data.remote.dto.HomeDto
import com.hossam.tawsel.feature_home.domain.model.Cancel
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: ITawselService
): IHomeRepository {

    /**
     * 1- change WorkState Connectivity
     * 2- Show Order Details
     * 3- Complete Client Order
     * 4- Cancel Client Order
     * */

    override fun fetchHome(): Flow<Resource<HomeDto>> = flow {
        try {
            emit(Resource.Loading())
            delay(1500L)
            val result = api.getHome()
            Log.i(TAG, "fetchHome: $result")
            emit(Resource.Success(result))
            return@flow
        } catch (e: IOException){
            Log.i(TAG, "fetchHome: $e")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "fetchHome: $e")
            when(e.code()){
                401 -> { emit(Resource.Error("UnAuth")) }
                else -> { emit(Resource.Error(Const.Exception_MESSAGE_HTTP)) }
            }
        }
    }


    override fun startOrder(orderId: String): Flow<Resource<Boolean>> = flow {
        try {
            val result = api.startOrder(orderId)
            emit(Resource.Success(result))
        } catch (e: IOException){
            Log.i(TAG, "startOrder: $e")
        } catch (e: HttpException){
            when(e.code()){
                401 -> { emit(Resource.Error("UnAuthorized")) }
                400 -> { emit(Resource.Error("Cant Start more Than One Order")) }
                else -> { emit(Resource.Error(Const.Exception_MESSAGE_HTTP)) }
            }
        }
    }

    override fun cancelOrder(cancel: Cancel): Flow<Resource<Boolean>> = flow {
        try {
            val result = api.cancelOrder(cancel)
            emit(Resource.Success(result))
        } catch (e: IOException){
            Log.i(TAG, "cancelOrder: $e")
        } catch (e: HttpException){
            Log.i(TAG, "cancelOrder: $e")
            when(e.code()){
                401 -> { emit(Resource.Error("UnAuthorized")) }
                400 -> { emit(Resource.Error("Cant Start more Than One Order")) }
                422 -> { emit(Resource.Error("The selected order id is invalid")) }
                else -> { emit(Resource.Error(Const.Exception_MESSAGE_HTTP)) }
            }
        }
    }

    override fun completeOrder(orderId: String): Flow<Resource<Boolean>> = flow {
        try {
            val result = api.completeOrder(orderId)
            emit(Resource.Success(result))
        } catch (e: IOException){
            Log.i(TAG, "completeOrder: $e")
        } catch (e: HttpException){
            Log.i(TAG, "completeOrder: $e")
            when(e.code()){
                401 -> { emit(Resource.Error("UnAuthorized")) }
                400 -> { emit(Resource.Error("Cant Start more Than One Order")) }
                422 -> { emit(Resource.Error("The selected order id is invalid")) }
                else -> { emit(Resource.Error(Const.Exception_MESSAGE_HTTP)) }
            }
        }
    }
}