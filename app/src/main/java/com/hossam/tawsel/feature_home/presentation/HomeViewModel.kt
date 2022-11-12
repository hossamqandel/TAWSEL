package com.hossam.tawsel.feature_home.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.DispatcherProvider
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.TempData
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.feature_home.domain.use_case.HomeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeUseCases: HomeUseCases,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val _orderId = MutableStateFlow(-1)
    val orderId = _orderId.asStateFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()


    init {
        getHome()
    }
    private fun getHome() = viewModelScope.launch(dispatcher.io){
        homeUseCases.getHomeUseCase().collectLatest { result ->
            when(result){
                is Resource.Loading -> { _uiChannel.send(UiEvent.Shimmer(isVisible = true)) }
                is Resource.Success -> {
                    result.data?.let { home ->
                        _uiChannel.send(UiEvent.Shimmer(isVisible = false))
                        val current = home.current

                        if (current != null){
                            _orderId.value = current.id
                            _state.value = state.value.copy(
                                currentLocation = current.address_details,

                                walletAmount = home.wallet ,
                                ordersCount = home.count,
                                hasOrder = true,
                                driverName = current.client,
                                time = "${current.date}, ${current.start}",
                                driverAvatar = TempData.DRIVER_PIC,
                                restaurantName = "UnKnown",
                                restaurantAvatar = TempData.RESTAURANT_PIC
                            )
                        } else {
                            _state.value = state.value.copy(
                                walletAmount = home.wallet,
                                ordersCount = home.count
                            )
                        }

                    }
                }
                is Resource.Error -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(result.message.toString()))
                    _uiChannel.send(UiEvent.Shimmer(isVisible = false))
                }
            }
        }
    }

//    fun onEvent(orderEvent: OrderEvent) = viewModelScope.launch(dispatcher.io){
//        when(orderEvent){
//            OrderEvent.StartOrder -> {
//                val direction = DriverHomeFragmentDirections.actionDriverHomeFragmentToDriverOrderDetailsFragment(_orderId.toString())
//                _uiChannel.send(UiEvent.Navigate(direction = direction))
//            }
//
//            is OrderEvent.CancelOrder -> {  }
//
//            OrderEvent.CompleteOrder -> {
//
//            }
//
//            OrderEvent.ShowOrderDetails -> {
//            val direction = DriverHomeFragmentDirections.actionDriverHomeFragmentToDriverOrderDetailsFragment(_orderId.toString())
//                _uiChannel.send(UiEvent.Navigate(direction = direction)) }
//        }
//    }



    fun onEvent(orderEvent: OrderEvent) = viewModelScope.launch(dispatcher.io){

        when(orderEvent){
            is OrderEvent.CancelOrder -> {  }

            OrderEvent.CompleteOrder -> { completeOrder() }

            OrderEvent.ShowOrderDetails -> {
                if (orderId.value != -1){
                    val direction = DriverHomeFragmentDirections.actionDriverHomeFragmentToDriverOrderDetailsFragment(orderId.value.toString())
                    _uiChannel.send(UiEvent.Navigate(direction = direction))
                }

            }

            OrderEvent.StartOrder -> { //TODO Nothing
            }
        }
    }

//    private fun startOrder() = viewModelScope.launch(dispatcher.io){
//        homeUseCases.startOrderUseCase(orderId.value.toString()).collectLatest { resource ->
//            when(resource){
//                is Resource.Loading -> {  }
//                is Resource.Success -> { _uiChannel.send(UiEvent.ShowSnackBar("Order Started")) }
//                is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString())) }
//            }
//        }
//    }

    private fun completeOrder() = viewModelScope.launch(dispatcher.io){
        homeUseCases.completeOrderUseCase(orderId.value.toString()).collectLatest { resource ->
            when(resource){
                is Resource.Loading -> {  }
                is Resource.Success -> { _uiChannel.send(UiEvent.ShowSnackBar("Order Completed")) }
                is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString())) }
            }
        }
    }

  //TODO add cancel order method
}