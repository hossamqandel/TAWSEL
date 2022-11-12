package com.hossam.tawsel.feature_notification.presntation.notifications

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hossam.tawsel.R
import com.hossam.tawsel.core.*
import com.hossam.tawsel.core.base.BaseFragment
import com.hossam.tawsel.databinding.FragmentNotificationsBinding
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDataDto
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotificationsFragment : BaseFragment<FragmentNotificationsBinding>(
    FragmentNotificationsBinding::inflate
) {

    private val viewModel: NotificationsViewModel by viewModels()
    private val mAdapter by lazy { NotificationsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClicks()
        stateCollector()
        channelCollector()
        backToHome()
    }

    private fun backToHome() {
        handleOnBackPressed {
            popUpBackStackThenNavigateUp()
//            findNavController().popBackStack()
            findNavController().navigateUp()
        }
    }


    private fun onClicks(){
        binding.apply {
            btnBack.onClick {
//                popUpBackStackThenNavigateUp()
            }
        }
    }

    private fun stateCollector() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { allNotifications ->
                setupRecycler(allNotifications)
            }
        }
    }

    private fun channelCollector(){
        lifecycleScope.launchWhenStarted {
            viewModel.uiChannel.collectLatest { uiEvent ->
               when(uiEvent){
                   is UiEvent.Navigate -> {}
                   is UiEvent.ProgressBar -> {}
                   is UiEvent.Shimmer -> { setupShimmer(uiEvent.isVisible) }
                   is UiEvent.ShowSnackBar -> { binding.guidelineTop.showSnackBar(uiEvent.message) }
               }
            }
        }
    }
    private fun setupRecycler(data: List<NotificationsDataDto>){
        binding.apply {
            mAdapter.setAdapterData(data)
            rvNotifications.adapter = mAdapter
        }
    }

    private fun setupShimmer(isVisible: Boolean){
        binding.shimmerNotifications.visibilityState(isVisible)
    }

}