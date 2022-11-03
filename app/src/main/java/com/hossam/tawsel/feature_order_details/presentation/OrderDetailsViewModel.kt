package com.hossam.tawsel.feature_order_details.presentation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.Const
import com.hossam.tawsel.core.DispatcherProvider
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.feature_home.domain.model.Cancel
import com.hossam.tawsel.feature_order_details.domain.use_case.OrderDetailsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val orderDetailsUseCases: OrderDetailsUseCases,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcher: DispatcherProvider
) : ViewModel() {


    private val _orderId = MutableStateFlow("-1")
    private val orderId = _orderId.asStateFlow()

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _state = MutableSharedFlow<OrderDetailsState>()
    val state = _state.asSharedFlow()

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()


    init {
        savedStateHandle.get<String>("orderId")?.let { orderId ->
            Log.i(TAG, "OrderDetailsViewModel --> $orderId ")
            _orderId.value = orderId
            onEvent(OrderDetailsEvent.FetchOrder)
        }
    }


    fun onEvent(orderDetailsEvent: OrderDetailsEvent) = viewModelScope.launch(dispatcher.io) {
        when (orderDetailsEvent) {
            is OrderDetailsEvent.FetchOrder -> {
                getOrderById()
            }
            is OrderDetailsEvent.CompleteOrder -> {
                completeOrderById()
            }
            is OrderDetailsEvent.CancelOrder -> {
                cancelOrderById(orderDetailsEvent.cancel)
            }
        }
    }


    private suspend fun getOrderById() = viewModelScope.launch(dispatcher.io) {
        orderDetailsUseCases.getOrderDetailsUseCase(orderId.value).collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    try {

                        resource.data?.let { orderDetails ->
                            val data = orderDetails.data
                            _phoneNumber.value = data.phone

                            val isStillActive = data.reasons != null

                            _state.emit(
                                OrderDetailsState(
                                    isActive = isStillActive,
                                    currentAddress = data.address.toString(),
                                    restaurantName = data.client,
                                    restaurantAddress = data.address_details,
                                    clientPhone = data.phone,
                                    time = "${data.date} ${data.start}",
                                    orderDetailsDto = orderDetails,
                                    clientInfos = mutableListOf(ClientInfo(title = "اسم العميل", value = data.client),
                                        ClientInfo(title = "عنوان العميل", value = data.address_details) ,
                                        ClientInfo(title = "طريقة الدفع", value = "Cash"),
                                        ClientInfo(title = "رقم العميل", value = data.phone)),
                                    items = orderDetails.data.items
                            )
                            )
                        }

//                        if (resource.data?.reason == null) {
//                            resource.data?.let { orderDetails ->
//                                orderDetails.apply {
//                                    _state.value = state.value.copy(
//                                        isCanceled = false,
//                                        currentAddress = addressDetails,
//                                        restaurantAddress = address.toString(),
//                                        restaurantName = client,
//                                        items = items
//                                    )
//                                }
//                            }
//
//                        } else {
//                            resource.data.let { orderDetails ->
//                                orderDetails.apply {
//                                    _state.value = state.value.copy(
//                                        isCanceled = true,
//                                        currentAddress = addressDetails,
//                                        restaurantAddress = address.toString(),
//                                        restaurantName = client,
//                                        items = items
//                                        )
//                                }
//                                _uiChannel.send(UiEvent.ShowSnackBar("This order already canceled"))
//                            }
//
//                        }
                    } catch (e: NullPointerException) {
                        Log.i(TAG, "getOrderById:  $e")
                    }
                }
                is Resource.Error -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString()))
                }
            }
        }
    }


    private suspend fun completeOrderById() = viewModelScope.launch(dispatcher.io) {
        orderDetailsUseCases.completeOrderUseCase(orderId.single()).collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(Const.ORDER_COMPLETED))
                }
                is Resource.Error -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString()))
                }
            }
        }
    }


    private suspend fun cancelOrderById(cancel: Cancel) = viewModelScope.launch(dispatcher.io) {
        orderDetailsUseCases.cancelOrderUseCase(cancel).collectLatest { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(Const.ORDER_CANCELED))
                }
                is Resource.Error -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString()))
                }
            }
        }
    }
}