package com.hossam.tawsel.feature_orders.data.repository

import com.hossam.tawsel.feature_main.data.remote.ITawselService
import com.hossam.tawsel.feature_orders.domain.model.OrdersDto
import com.hossam.tawsel.feature_orders.domain.repository.IOrdersRepository
import javax.inject.Inject

class OrdersRepositoryImpl @Inject constructor(
    private val api: ITawselService
): IOrdersRepository {

    override suspend fun getOrders(): OrdersDto {
        return api.getOrders()
    }

//        try {
//            emit(Resource.Loading())
//            emit(Resource.Success(result))
//            return@flow
//        } catch (e: IOException){
//            Log.i(TAG, "getOrders: $e")
//        } catch (e: HttpException){
//            Log.i(TAG, "getOrders: $e")
//        }

}