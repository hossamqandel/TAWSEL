package com.hossam.tawsel.feature_order_details.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.hossam.tawsel.core.Const
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import com.hossam.tawsel.feature_order_details.data.mapper.toOrderDetails
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto
import com.hossam.tawsel.feature_order_details.domain.model.OrderDetails
import com.hossam.tawsel.feature_order_details.domain.repository.IOrderDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class OrderDetailsRepositoryImpl @Inject constructor(
    private val api: ITawselService
): IOrderDetailsRepository {

    override fun getOrderDetailsById(orderId: String): Flow<Resource<OrderDetailsDto>> = flow {
        try {
            emit(Resource.Loading())
            val result = api.singleOrder(orderId = orderId)
            emit(Resource.Success(result))
            return@flow
        } catch (e: Exception) {

        } catch (e: IOException){
            Log.i(TAG, "getOrderDetailsById: $e")
            emit(Resource.Error(Const.Exception_MESSAGE_IO))
        } catch (e: HttpException){
            Log.i(TAG, "getOrderDetailsById: $e")
            when(e.code()){
                401 -> { emit(Resource.Error("UnAuth")) }
                else -> { emit(Resource.Error(Const.Exception_MESSAGE_HTTP)) }
            }
        }
    }
}