package com.hossam.tawsel.feature_profile.presentation.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.DispatcherProvider
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.feature_profile.domain.model.UpdatePassword
import com.hossam.tawsel.feature_profile.domain.use_case.ProfileUseCases
import com.hossam.tawsel.feature_profile.presentation.profile.ProfileEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    fun onUpdatePassword(profileEvent: ProfileEvent) = viewModelScope.launch(dispatcher.io){
        if (profileEvent is ProfileEvent.UpdatePasswordInfo){
            profileUseCases.updatePasswordUseCase(profileEvent.updatePassword).collectLatest { resource ->
                when(resource){
                    is Resource.Loading -> {}
                    is Resource.Success -> { _uiChannel.send(UiEvent.ShowSnackBar("Password Updated successfully")) }
                    is Resource.Error -> { _uiChannel.send(UiEvent.ShowSnackBar(resource.message.toString())) }
                }
            }
        }
    }
}