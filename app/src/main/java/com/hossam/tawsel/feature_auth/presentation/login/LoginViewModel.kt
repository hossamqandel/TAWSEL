package com.hossam.tawsel.feature_auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hossam.tawsel.core.DispatcherProvider
import com.hossam.tawsel.core.Nav
import com.hossam.tawsel.core.Resource
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_auth.domain.use_case.AuthUseCases
import com.hossam.tawsel.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCases: AuthUseCases,
    private val dispatcherProvider: DispatcherProvider
): ViewModel() {

    private val _uiChannel = Channel<UiEvent>()
    val uiChannel = _uiChannel.receiveAsFlow()

    private fun doLogin(login: Login) = viewModelScope.launch(dispatcherProvider.io) {
        useCases.loginUseCase(login).collectLatest { response ->
            when(response){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    _uiChannel.send(UiEvent.SnackBar("Login Successfully"))
//                    _uiChannel.send(UiEvent.Navigate(destination = Nav))
                }
                is Resource.Error -> _uiChannel.send(UiEvent.SnackBar(response.message.toString()))
            }
        }
    }

    private fun doReg(){

    }


    fun onEvent(authEvent: AuthEvent){

        when(authEvent){
            is AuthEvent.MakeLogin -> doLogin(authEvent.login!!)
        }
    }

}