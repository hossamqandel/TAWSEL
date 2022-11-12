package com.hossam.tawsel.feature_orders.presentation

import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.core.base.BaseFragment
import com.hossam.tawsel.core.showSnackBar
import com.hossam.tawsel.core.showToast
import com.hossam.tawsel.core.visibilityState
import com.hossam.tawsel.databinding.FragmentOrdersBinding
import com.hossam.tawsel.feature_map.presentation.util.LocationUtil
import com.hossam.tawsel.feature_orders.domain.model.DataDto
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding>(FragmentOrdersBinding::inflate) {

    private val viewModel: OrdersViewModel by viewModels()
    private val mOrdersAdapter by lazy { OrdersAdapter() }
    private var isLocationPermissionsGranted = false
    @Inject lateinit var _mOrdersFilterAdapter: Lazy<OrdersFilterAdapter>
    private val mOrdersFilterAdapter get() = _mOrdersFilterAdapter.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOrdersFilterRecycler()
        stateCollector()
        ordersAdapterOnClickEvents()
        uiCollector()
    }

    private fun stateCollector(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.state.collect {
                    withContext(Dispatchers.Main){
                        setupOrdersRecycler(it)
                    }
                }
            }
        }
    }


    private fun uiCollector(){
        lifecycleScope.launch {
            viewModel.uiChannel.collectLatest { uiEvent ->
                when(uiEvent){
                    is UiEvent.Navigate -> findNavController().navigate(uiEvent.direction!!)
                    is UiEvent.ProgressBar -> {}
                    is UiEvent.ShowSnackBar -> binding.ivDriver.showSnackBar(uiEvent.message)
                    is UiEvent.Shimmer -> { setupShimmerWithRecyclerState(uiEvent.isVisible) }
                }
            }
        }
    }

    private fun setupShimmerWithRecyclerState(isVisible: Boolean){
        binding.shimmerOrders.visibilityState(isVisible)
        if (isVisible) binding.rvOrders.isVisible = false
         else binding.rvOrders.isVisible = true
    }

    private fun setupOrdersFilterRecycler(){
        binding.apply {
            rvFilterTypes.adapter = mOrdersFilterAdapter
            mOrdersFilterAdapter.onItemClick = {
                Toast.makeText(requireContext(), it::class.simpleName, Toast.LENGTH_SHORT).show()
                viewModel.onEvent(it)
            }
        }
    }
    private fun setupOrdersRecycler(data: List<DataDto>){
        binding.apply {
            mOrdersAdapter.setOrdersList(data)
            rvOrders.adapter = mOrdersAdapter
            for (i in data){
                Log.i(TAG, "setupOrdersRecycler: ${i.status}")
            }
        }

    }
    private fun ordersAdapterOnClickEvents(){
        mOrdersAdapter.onDetailsItemClick = { orderId ->
            Log.i(TAG, "ordersAdapterOnclickEvents: Det $orderId")
            sendViewModelOrderActionEvent(OrdersEvent.InDetails(orderId))
        }

        mOrdersAdapter.onFinishItemClick = { orderId ->
            Log.i(TAG, "ordersAdapterOnclickEvents: Fins $orderId")
            sendViewModelOrderActionEvent(OrdersEvent.Finish(orderId))
        }

        mOrdersAdapter.onStartItemClick = { orderId ->
            Log.i(TAG, "ordersAdapterOnclickEvents: Start $orderId")
            sendViewModelOrderActionEvent(OrdersEvent.Start(orderId))
        }

        mOrdersAdapter.onCancelItemClick = { cancel ->
            Log.i(TAG, "ordersAdapterOnclickEvents: Cancel $cancel")
            sendViewModelOrderActionEvent(OrdersEvent.CancelOrder(cancel))
        }
    }
    private fun sendViewModelOrderActionEvent(event: OrdersEvent){
        when(event){
            is OrdersEvent.Start -> viewModel.onOrderAction(event)
            is OrdersEvent.Finish -> viewModel.onOrderAction(event)
            is OrdersEvent.CancelOrder -> viewModel.onOrderAction(event)
            is OrdersEvent.InDetails -> viewModel.onOrderAction(event)
        }
    }

    private fun getLocationPermission(){
        Log.i(TAG, "getLocationPermission: getting location permissions")
        context?.let {
            if (ContextCompat.checkSelfPermission(it.applicationContext, LocationUtil.FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                if (ContextCompat.checkSelfPermission(it.applicationContext, LocationUtil.COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    isLocationPermissionsGranted = true
                }
                else {
                    ActivityCompat.requestPermissions(requireActivity(), LocationUtil.ARRAY_FINE_AND_COARSE_LOCATION, LocationUtil.LOCATION_PERMISSION_REQUEST_CODE)
                }
            } else {
                ActivityCompat.requestPermissions(requireActivity(), LocationUtil.ARRAY_FINE_AND_COARSE_LOCATION, LocationUtil.LOCATION_PERMISSION_REQUEST_CODE)

            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        Log.i(TAG, "onRequestPermissionsResult: Called")
        when(requestCode){
            LocationUtil.LOCATION_PERMISSION_REQUEST_CODE -> {
                if ( grantResults.isNotEmpty()){
                    for (i in grantResults){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            isLocationPermissionsGranted = false
                            Log.i(TAG, "onRequestPermissionsResult: Permission Failed")
                        }
                        requireContext().showToast("Permission Denied")
                    }
                    isLocationPermissionsGranted = true
                    Log.i(TAG, "onRequestPermissionsResult: Permission Granted")
                    // initialize the Map
                }
            }
        }
    }
}