package com.hossam.tawsel.feature_order_details.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.hossam.tawsel.core.*
import com.hossam.tawsel.core.base.BaseFragment
import com.hossam.tawsel.databinding.FragmentDriverOrderDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject


@AndroidEntryPoint
class DriverOrderDetailsFragment : BaseFragment<FragmentDriverOrderDetailsBinding>(FragmentDriverOrderDetailsBinding::inflate) {

    private val CALL_REQUEST_CODE by lazy { 20 }
    private val viewModel: OrderDetailsViewModel by viewModels()
    private val mOrderDetailsAdapter by lazy { OrderDetailsAdapter() }
    private val mClientInfoAdapter by lazy { ClientInfoAdapter() }
    @Inject lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClicks()
        stateCollector()
        uiCollector()
    }

    private fun onClicks(){
        binding.apply {
            btnCallClient.onClick {
                if (viewModel.phoneNumber.value.isNotBlank()){
                    contactClient(viewModel.phoneNumber.value)
                }
            }
        }
    }

    private fun setupRecyclers(orderDetailsState: OrderDetailsState){
        binding.apply {
            rvOrderDetail.apply {
                mOrderDetailsAdapter.setOrderDetailsData(orderDetailsState.orderDetailsDto!!)
                this.adapter = mOrderDetailsAdapter
            }

            rvClientInfo.apply {
                mClientInfoAdapter.setClientInfo(orderDetailsState.clientInfos)
                this.adapter = mClientInfoAdapter
            }
        }
    }

    private fun stateCollector(){
        binding.apply {
            lifecycleScope.launchWhenStarted {
                viewModel.state.collectLatest { orderDetailsState ->
                    setupViews(orderDetailsState)
                    orderDetailsState.orderDetailsDto?.let {
                        setupRecyclers(orderDetailsState)
                    }
                }
            }
        }
    }
    private fun uiCollector(){
        binding.apply {
            collectLatestLifecycleFlow(viewModel.uiChannel){ event ->
                when(event){
                    is UiEvent.Navigate -> {}
                    is UiEvent.ProgressBar -> {}
                    is UiEvent.ShowSnackBar -> tvOrderLocation.showSnackBar(event.message)
                    is UiEvent.Shimmer -> {}
                }
            }
        }
    }

    private fun setupViews(orderDetailsState: OrderDetailsState){
        binding.apply {
            val orderCancellationCard = listOf(cvOrderCancellationDetails, tvReason, tvClientReason, btnCancelOrder, btnCompleteOrder)

//            materialCardView4.setMargins(bottomMarginDp = 100, topMarginDp = 100)

                if (!orderDetailsState.isActive){
                    orderCancellationCard.goneAll()
                } else {
                    orderCancellationCard.visibleAll()
                    tvClientReason.text = orderDetailsState.reason
                }


            orderDetailsState.apply {
                glide.load(TempData.RESTAURANT_PIC).into(ivRestaurantLogo)
                tvPaymentType.text = paymentType
                tvOrderLocation.text = restaurantAddress
                tvRestaurantName.text = restaurantName
                tvOrderDropLocation.text = currentAddress
                tvTime.text = time
            }
        }
    }

    private fun contactClient(phone: String){
        if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                CALL_REQUEST_CODE)
        } else {
            val intent by lazy { Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone")) }
            startActivity(intent)
        }

    }
}