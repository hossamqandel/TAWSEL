package com.hossam.tawsel.feature_home.domain.repository

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_home.data.remote.dto.HomeDto
import com.hossam.tawsel.feature_home.domain.model.Cancel
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {

    fun fetchHome(): Flow<Resource<HomeDto>>
    fun startOrder(orderId: String): Flow<Resource<Boolean>>
    fun cancelOrder(cancel: Cancel): Flow<Resource<Boolean>>
    fun completeOrder(orderId: String): Flow<Resource<Boolean>>
}