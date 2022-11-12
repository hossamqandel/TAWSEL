package com.hossam.tawsel.feature_notification.presntation.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.DispatcherProvider
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDataDto
import com.hossam.tawsel.feature_notification.domain.use_case.NotificationUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val useCases: NotificationUseCases,
    private val dispatcher: DispatcherProvider
): ViewModel(){

    private val _state = MutableStateFlow(emptyList<NotificationsDataDto>())
    val state = _state.asStateFlow()

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() = viewModelScope.launch(dispatcher.io){
        useCases.getNotificationsUseCase().collectLatest { resource ->
            when(resource){
                is Resource.Loading -> { _uiChannel.send(UiEvent.Shimmer(isVisible = true)) }
                is Resource.Success -> {
                    resource.data?.let { _state.value = resource.data.data }
                    _uiChannel.send(UiEvent.Shimmer(isVisible = false))
                }
                is Resource.Error -> {
                    _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString()))
                    _uiChannel.send(UiEvent.Shimmer(isVisible = false))
                }
            }
        }
    }
}