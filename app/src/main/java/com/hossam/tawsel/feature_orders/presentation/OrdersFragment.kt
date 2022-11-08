package com.hossam.tawsel.feature_orders.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.hossam.tawsel.core.UiEvent
import com.hossam.tawsel.core.showSnackBar
import com.hossam.tawsel.databinding.FragmentOrdersBinding
import com.hossam.tawsel.feature_orders.domain.model.DataDto
import dagger.Lazy
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class OrdersFragment : Fragment() {

    private var _binding: FragmentOrdersBinding? = null
    private val binding get() = _binding!!
    private val viewModel: OrdersViewModel by viewModels()
    private val mOrdersAdapter by lazy { OrdersAdapter() }
    @Inject lateinit var _mOrdersFilterAdapter: Lazy<OrdersFilterAdapter>
    private val mOrdersFilterAdapter get() = _mOrdersFilterAdapter.get()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupOrdersFilterRecycler()
        stateCollector()
        ordersAdapterOnClickEvents()
        uiCollector()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                }
            }
        }
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
}