package com.hossam.tawsel.feature_profile.presentation.change_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hossam.tawsel.core.*
import com.hossam.tawsel.databinding.FragmentChangePasswordBinding
import com.hossam.tawsel.feature_profile.domain.model.UpdatePassword
import com.hossam.tawsel.feature_profile.presentation.profile.ProfileEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ChangePasswordViewModel by viewModels()
    private var isPassTextInputVisible = false
    private var isPassConfirmationTextInputVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
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
            btnSaveChanges.onClick {
                val password = etNewPass.text.toString().trim()
                val passwordConfirm = etNewPassConfirm.text.toString().trim()
                val updatePassword = UpdatePassword(password, passwordConfirm)
                viewModel.onUpdatePassword(ProfileEvent.UpdatePasswordInfo(updatePassword))
            }

            btnShowHidePass.onClick {
                etNewPass.onTextInputVisibilityChange()
            }

            btnShowHidePassConfirmation.onClick {
                etNewPassConfirm.onTextInputVisibilityChange()
            }
        }

    }

    private fun collectUiEvents(){
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.uiChannel.collectLatest { event ->
                    when(event){
                        is UiEvent.Navigate -> {}
                        is UiEvent.ProgressBar -> {}
                        is UiEvent.ShowSnackBar ->{
                            etNewPass.showSnackBar(event.message)
                            val clearAll = listOf(etNewPass,etNewPassConfirm)
                                clearAll.apply { this.onEach { it.text.clear() } }
                        }
                    }
                }
            }
        }
    }

}