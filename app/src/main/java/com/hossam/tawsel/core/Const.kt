package com.hossam.tawsel.core

import com.hossam.tawsel.R

object Const {


    val BASE_URL by lazy { "https://www.tawseleg.com/api/" }
    val Exception_MESSAGE_IO by lazy { "Couldn't reach server. Check your internet connection" }
    val Exception_MESSAGE_HTTP by lazy { "An expected error occurred" }
    val ORDER_STARTED by lazy { "Order has started" }
    val ORDER_COMPLETED by lazy { "Order completed" }
    val ORDER_CANCELED by lazy { "The order has been cancelled" }




    val ONE_SECOND: Long by lazy { 1000 }
    val NULL_TEXT_FORM by lazy { "The order has been cancelled" }



}