package com.hossam.tawsel.feature_home.domain.use_case

import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.feature_home.domain.model.Cancel
import com.hossam.tawsel.feature_home.domain.repository.IHomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val repo: IHomeRepository
) {

    operator fun invoke(cancel: Cancel): Flow<Resource<Boolean>> {
        return repo.cancelOrder(cancel)
    }
}