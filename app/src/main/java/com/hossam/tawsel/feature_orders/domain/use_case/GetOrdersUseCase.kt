package com.hossam.tawsel.feature_orders.domain.use_case

import android.content.ContentValues.TAG
import android.util.Log
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_orders.domain.model.DataDto
import com.hossam.tawsel.feature_orders.domain.model.OrdersDto
import com.hossam.tawsel.feature_orders.domain.repository.IOrdersRepository
import com.hossam.tawsel.feature_orders.presentation.OrdersBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val repo: IOrdersRepository
) {

    operator fun invoke(event: OrdersBy): Flow<Resource<List<DataDto>>> = flow {
        try {
            emit(Resource.Loading())
            val filterByAll = repo.getOrders().data
            val filterByNewOrders = repo.getOrders().data.filter { it.status.lowercase() == "in stock" }
            val filterByCurrentOrders = repo.getOrders().data.filter { it.status.lowercase() == "started" }
            val filterByCompletedOrCanceled = repo.getOrders().data.filter { it.status.lowercase() == "completed" || it.status.lowercase() == "OrCanceled" }

            when(event){
                OrdersBy.All -> emit(Resource.Success(filterByAll))
                OrdersBy.InStock -> emit(Resource.Success(filterByNewOrders))
                OrdersBy.Started -> emit(Resource.Success(filterByCurrentOrders))
                OrdersBy.Completed -> emit(Resource.Success(filterByCompletedOrCanceled))
            }

            return@flow

        } catch (e: IOException){
            Log.i(TAG, "invoke: $e")
        } catch (e: HttpException){
            Log.i(TAG, "invoke: $e")
        }
    }
}