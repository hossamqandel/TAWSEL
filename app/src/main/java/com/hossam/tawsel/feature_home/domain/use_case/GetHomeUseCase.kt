package com.hossam.tawsel.feature_home.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_home.data.remote.dto.HomeDto
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import com.hossam.tawsel.feature_main.data.remote.ITawselService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetHomeUseCase (
  private val repo: IHomeRepository
) {

    operator fun invoke(): Flow<Resource<HomeDto>> {
        val result = repo.fetchHome()

       return result.map { response ->

        when(response){
            is Resource.Loading -> { Resource.Loading() }
            is Resource.Success -> { (Resource.Success(response.data!!)) }
            is Resource.Error -> {
                if (response.data?.error != null){
                    (Resource.Error(response.data.error.toString()))
                }

                else if (response.data?.message != null){
                    (Resource.Error(response.data.error.toString()))
                }
                else {
                    Resource.Error(response.message.toString()) }
                }
        }

        }
    }


}