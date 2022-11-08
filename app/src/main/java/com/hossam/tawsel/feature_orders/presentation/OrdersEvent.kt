package com.hossam.tawsel.feature_orders.presentation

import com.hossam.tawsel.feature_home.domain.model.Cancel

sealed interface OrdersEvent {
    data class Start(val orderId: Int): OrdersEvent
    data class Finish(val orderId: Int): OrdersEvent
    data class InDetails(val orderId: Int): OrdersEvent
    data class CancelOrder(val cancel: Cancel): OrdersEvent
}