package com.hossam.tawsel.feature_auth.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.core.base.BaseFragment
import com.hossam.tawsel.core.navigate
import com.hossam.tawsel.core.onClick
import com.hossam.tawsel.core.showSnackBar
import com.hossam.tawsel.databinding.FragmentLoginBinding
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
        collectUiEvents()
    }


    private fun onClicks(){
        binding.apply {
            btnLogin.onClick {
                val mPhone = etPhoneNum.text.toString()
                val mPassword = etPass.text.toString()
                viewModel.onEvent(AuthEvent.MakeLogin(Login(mPhone, mPassword)))
            }

            tvForgetPass.onClick {
                viewModel.onEvent(AuthEvent.ForgetPassword)
            }
        }
    }

    private fun collectUiEvents(){
        lifecycleScope.launchWhenStarted {
            viewModel.uiChannel.collect { uiEvent ->
                when(uiEvent){
                    is UiEvent.ShowSnackBar -> binding.btnLogin.showSnackBar(uiEvent.message)
                    is UiEvent.Navigate -> { navigate(uiEvent.destination) }
                    is UiEvent.ProgressBar -> {}
                    is UiEvent.Shimmer -> {}
                }
            }
        }
    }

}