package com.hossam.tawsel.feature_orders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.DispatcherProvider
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.feature_home.domain.use_case.HomeUseCases
import com.hossam.tawsel.feature_home.presentation.OrderEvent
import com.hossam.tawsel.feature_orders.domain.model.DataDto
import com.hossam.tawsel.feature_orders.domain.use_case.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val orderActionUseCases: HomeUseCases,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val _state = MutableStateFlow(emptyList<DataDto>())
    val state = _state.asStateFlow()

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    init {
        onEvent(OrdersBy.All)
    }

    fun onEvent(event: OrdersBy) = viewModelScope.launch(dispatcher.io){
        getOrdersUseCase(event).collectLatest { result ->
            when(result){
                is Resource.Loading -> {}
                is Resource.Success -> { _state.value = result.data ?: emptyList() }
                is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(result.message.toString())) }
            }
        }
    }


    fun onOrderAction(event: OrdersEvent) = viewModelScope.launch(dispatcher.io){
        when(event){
            is OrdersEvent.Start -> { orderActionUseCases.startOrderUseCase(event.orderId.toString()).collectLatest { resource ->
                when(resource){
                    is Resource.Loading -> {  }
                    is Resource.Success -> { _uiChannel.send(UiEvent.ShowSnackBar("Order started successfully")) }
                    is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString())) } }
            } }


            is OrdersEvent.Finish -> { orderActionUseCases.completeOrderUseCase(event.orderId.toString()).collectLatest { resource ->
                when(resource){
                    is Resource.Loading -> {  }
                    is Resource.Success -> { _uiChannel.send(UiEvent.ShowSnackBar("Order finished successfully")) }
                    is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString())) } }
            } }


            is OrdersEvent.CancelOrder -> { orderActionUseCases.cancelOrderUseCase(event.cancel).collectLatest { resource ->
                when(resource){
                    is Resource.Loading -> {  }
                    is Resource.Success -> { _uiChannel.send(UiEvent.ShowSnackBar("Order canceled successfully")) }
                    is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString())) }
                }
            } }


            is OrdersEvent.InDetails -> {
                val navDir = OrdersFragmentDirections.actionOrdersFragmentToDriverOrderDetailsFragment(event.orderId.toString())
                _uiChannel.send(UiEvent.Navigate(direction = navDir)) }
        }
    }
}