package com.hossam.tawsel.feature_profile.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.hossam.tawsel.R
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.core.base.BaseFragment
import com.hossam.tawsel.core.onClick
import com.hossam.tawsel.core.showSnackBar
import com.hossam.tawsel.databinding.FragmentProfileBinding
import com.hossam.tawsel.feature_profile.domain.model.UpdateProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()
    @Inject lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()
        collectState()
        collectUi()
    }

    private fun onClicks(){
        binding.apply {
            btnSaveChanges.onClick {
                val name = etFullName.text.toString().trim()
                val phone = etPhoneNum.text.toString().trim()
                val address = etAddress.text.toString().trim()
//                viewModel.onEvent(ProfileEvent.UpdateProfileInfo(UpdateProfile(
//                    name, phone, address
//                )))
                viewModel.onEvent(ProfileEvent.GetProfile)
            }
        }
    }

    private fun collectState(){
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { data ->
                setupViews(data)
            }
        }
    }

    private fun setupViews(profileState: ProfileState){
        binding.apply {
            etFullName.setText(profileState.name)
            etPhoneNum.setText(profileState.phone)
            etAddress.setText(profileState.address)
            glide.load(profileState.avatar).into(ivDriverAvatar)
        }
    }

    private fun collectUi(){
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.uiEvent.collectLatest { event ->
                    when(event){
                        is UiEvent.Navigate -> { }
                        is UiEvent.ProgressBar -> { }
                        is UiEvent.ShowSnackBar -> { etFullName.showSnackBar(event.message) }
                        is UiEvent.Shimmer -> {}
                    }
                }
            }
        }
    }

}