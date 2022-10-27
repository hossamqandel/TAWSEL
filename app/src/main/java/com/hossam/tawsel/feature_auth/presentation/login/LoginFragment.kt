package com.hossam.tawsel.feature_auth.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.core.onClick
import com.hossam.tawsel.core.showSnackBar
import com.hossam.tawsel.databinding.FragmentLoginBinding
import com.hossam.tawsel.feature_auth.domain.model.Login
import com.hossam.tawsel.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
        collectUiEvents()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClicks(){
        binding.apply {
            btnLogin.onClick {
                val mPhone = etPhoneNum.text.toString()
                val mPassword = etPass.text.toString()
                viewModel.onEvent(AuthEvent.MakeLogin(Login(mPhone, mPassword)))
            }
        }
    }

    private fun collectUiEvents(){
        lifecycleScope.launchWhenStarted {
            viewModel.uiChannel.collect { uiEvent ->
                when(uiEvent){
                    is UiEvent.SnackBar -> binding.btnLogin.showSnackBar(uiEvent.message)
                    is UiEvent.Navigate -> {}
                    is UiEvent.ProgressBar -> {}
                }
            }
        }
    }

}