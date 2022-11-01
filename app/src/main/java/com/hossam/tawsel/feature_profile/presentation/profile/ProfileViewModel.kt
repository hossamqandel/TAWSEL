package com.hossam.tawsel.feature_profile.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.*
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_profile.domain.model.UpdateProfile
import com.hossam.tawsel.feature_profile.domain.use_case.ProfileUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCases: ProfileUseCases,
    private val dispatcher: DispatcherProvider
): ViewModel() {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        onEvent(ProfileEvent.GetProfile)
    }

    fun onEvent(profileEvent: ProfileEvent) = viewModelScope.launch(dispatcher.io){
        when(profileEvent){
            is ProfileEvent.GetProfile -> { getProfile() }
            is ProfileEvent.UpdateProfileInfo -> { updateProfile(profileEvent.updateProfile) }
            else -> {}
        }
    }

    private fun getProfile() = viewModelScope.launch(dispatcher.io){
        val phone = SharedPref.getUserPhone()
        val pass = SharedPref.getUserPassword()
        val login = Login(phone, pass)
        profileUseCases.getProfileUseCase(login).collectLatest { resource ->
            when(resource){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    resource.data?.let {
                        it.apply {
                            _state.value = state.value.copy(
                                name = name,
                                phone = phone,
                                address = address,
                                avatar = TempData.DRIVER_PIC
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar(resource.message.toString()))
                }
            }
        }
    }

    private fun updateProfile(updateProfile: UpdateProfile) = viewModelScope.launch(dispatcher.io){
        //TODO the actually api fun return type should change because you will refresh ui data again by send GetProfile Event
        // when you make update profile action
        profileUseCases.updateProfileUseCase(updateProfile).collectLatest { resource ->
            when(resource){
                is Resource.Loading -> {}
                is Resource.Success -> { _uiEvent.send(UiEvent.ShowSnackBar("Profile Data Updated Successfully")) }
                is Resource.Error -> { _uiEvent.send(UiEvent.ShowSnackBar(resource.message.toString())) }
            }
        }
    }
}