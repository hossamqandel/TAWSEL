package com.hossam.tawsel.feature_order_details.presentation

import com.hossam.tawsel.feature_home.domain.model.Cancel

sealed interface OrderDetailsEvent {
    object FetchOrder: OrderDetailsEvent
    object CompleteOrder: OrderDetailsEvent
    data class CancelOrder(val cancel: Cancel): OrderDetailsEvent
}