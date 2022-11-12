package com.hossam.tawsel.feature_home.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.google.android.material.R.attr.colorOnSurface
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.hossam.tawsel.R
import com.hossam.tawsel.core.*
import com.hossam.tawsel.databinding.FragmentDriverHomeBinding
import com.hossam.tawsel.feature_home.domain.model.Cancel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class DriverHomeFragment : Fragment() {

    private var _binding: FragmentDriverHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    @Inject lateinit var glide: RequestManager
    private var orderCancelationnStatus = 0
    private val mBottomSheetDialog by lazy { BottomSheetDialog(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDriverHomeBinding.inflate(inflater, container, false)
        Log.i(TAG, "onCreateView: ")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: ")

        onClicks()
        stateCollector()
        uiChannelCollector()
    }


    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    private fun onClicks(){
        binding.apply {
            stbConnectivity.setOnCheckedChangeListener { compoundButton, b ->
                if (b){
                    stbConnectivity.changeTrackTint(R.color.orange)
                } else {
                    stbConnectivity.changeTrackTint(colorOnSurface)
                }
            }

            btnShowOrderDetails.onClick { viewModel.onEvent(OrderEvent.ShowOrderDetails) }
            btnCancelOrder.onClick {
                showBottomSheetCancelDialog()
            }
            btnCompleteOrder.onClick { viewModel.onEvent(OrderEvent.CompleteOrder) }

            ivNotification.onClick {
                navigate(Nav.HOME_TO_NOTIFICATIONS)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.i(TAG, "onDestroyView: ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy: ")
    }

    private fun stateCollector(){
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                viewModel.state.collectLatest { homeState ->
                    setupViews(homeState)
                }
            }
        }
    }

    private fun setupViews(homeState: HomeState){
        binding.apply {
            homeState.apply {
                tvCurrentLocation.text = currentLocation
                tvDriverName.text = driverName
                tvOrderQuantity.text = "$ordersCount ج.م"
                tvPaymentType.text = paymentType
                cvOrderBoard.isVisible = hasOrder
                tvRequiredMony.text = "$walletAmount ج.م"
                tvDriverAddress.text = currentLocation
                tvCurrentLocation2.text = currentLocation
                tvCurrentLocation3.text = currentLocation
                tvRestaurantName.text = restaurantName
                tvTime.text = time
                glide.load(driverAvatar).into(ivDriver)
                glide.load(restaurantAvatar).into(ivRestaurantLogo)
            }
        }
    }

    private fun setupShimmerWithMainUiFrame(isVisible: Boolean){
        binding.shimmerHome.visibilityState(isVisible)
        if (!isVisible){
            binding.uiFrame.isVisible = true
        }
    }

    private fun uiChannelCollector(){
        binding.apply {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.uiChannel.collectLatest { uiEvent ->
                    when(uiEvent){
                        is UiEvent.Navigate -> {
                            if (uiEvent.direction != null){ navDirection(uiEvent.direction) }
                            else { navigate(uiEvent.destination) } }
                        is UiEvent.ProgressBar -> {}
                        is UiEvent.ShowSnackBar -> ivDriver.showSnackBar(uiEvent.message)
                        is UiEvent.Shimmer -> { setupShimmerWithMainUiFrame(uiEvent.isVisible) }
                    }
                }
            }
        }
    }

    private fun showBottomSheetCancelDialog(){

        mBottomSheetDialog.setContentView(R.layout.bottom_sheet_cancel)
        val mBtnCanceled by lazy { mBottomSheetDialog.findViewById<MaterialCardView>(R.id.btn_UserCanceled) }
        val mBtnDamaged by lazy { mBottomSheetDialog.findViewById<MaterialCardView>(R.id.btn_OrderDamaged) }
        val mBtnOtherReason by lazy { mBottomSheetDialog.findViewById<ImageView>(R.id.btn_OtherReason) }
        val mEtReason by lazy { mBottomSheetDialog.findViewById<EditText>(R.id.et_Reason) }
        val mBtnSend by lazy { mBottomSheetDialog.findViewById<MaterialButton>(R.id.btn_Send) }
        val mBtnDismissSheet by lazy { mBottomSheetDialog.findViewById<ImageView>(R.id.btn_Close) }

        mBtnCanceled?.onClick {
            if (orderCancelationnStatus != 1){
                setOnOrderOptionCanceledByUser(true)
                setOnOrderOptionDamaged(false)
                setOnOrderOptionByOtherReason(false)
            }
        }

        mBtnDamaged?.onClick {
            if (orderCancelationnStatus != 2){
                setOnOrderOptionDamaged(true)
                setOnOrderOptionCanceledByUser(false)
                setOnOrderOptionByOtherReason(false)
            }
        }

        mBtnOtherReason?.onClick {
            if (orderCancelationnStatus != 3){
                setOnOrderOptionByOtherReason(true)
                setOnOrderOptionDamaged(false)
                setOnOrderOptionCanceledByUser(false)
            }
        }


        mBtnSend?.onClick {
            when(orderCancelationnStatus){
                1 -> sendCanceledEvent("العميل الغى الطلب")
                2 ->  sendCanceledEvent("الطلب تالف")
                3 -> { val mTextReason = mEtReason?.text.toString().trim()
                    sendCanceledEvent(mTextReason) }
            }
        }

        mBtnDismissSheet?.onClick { mBottomSheetDialog.showHideBottomSheetDialog(false) }
        mBottomSheetDialog.showHideBottomSheetDialog(true)
    }

    private fun sendCanceledEvent(reason: String){
        viewModel.onEvent(OrderEvent.CancelOrder(Cancel(viewModel.orderId.value, reason)))
    }

    private fun setOnOrderOptionCanceledByUser(isSelected: Boolean){
        when(isSelected){
            true -> {
                orderCancelationnStatus = 1
                mBottomSheetDialog.findViewById<ImageView>(R.id.iv_Canceled)?.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_checked_white))
                mBottomSheetDialog.findViewById<MaterialCardView>(R.id.btn_UserCanceled)?.setBackgroundColor(resources.getColor(R.color.orange))
                mBottomSheetDialog.findViewById<TextView>(R.id.tv_Canceled)?.setTextColor(resources.getColor(R.color.white))
            }
            else -> {
                mBottomSheetDialog.findViewById<ImageView>(R.id.iv_Canceled)?.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_unchecked))
                mBottomSheetDialog.findViewById<MaterialCardView>(R.id.btn_UserCanceled)?.setBackgroundColor(resources.getColor(R.color.white))
                mBottomSheetDialog.findViewById<TextView>(R.id.tv_Canceled)?.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

    private fun setOnOrderOptionDamaged(isSelected: Boolean){
        when(isSelected){
            true -> {
                orderCancelationnStatus = 2
                mBottomSheetDialog.findViewById<ImageView>(R.id.iv_Damaged)?.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_checked_white))
                mBottomSheetDialog.findViewById<MaterialCardView>(R.id.btn_OrderDamaged)?.setBackgroundColor(resources.getColor(R.color.orange))
                mBottomSheetDialog.findViewById<TextView>(R.id.tv_Damaged)?.setTextColor(resources.getColor(R.color.white))
            }
            else -> {
                mBottomSheetDialog.findViewById<ImageView>(R.id.iv_Damaged)?.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_unchecked))
                mBottomSheetDialog.findViewById<MaterialCardView>(R.id.btn_OrderDamaged)?.setBackgroundColor(resources.getColor(R.color.white))
                mBottomSheetDialog.findViewById<TextView>(R.id.tv_Damaged)?.setTextColor(resources.getColor(R.color.black))
            }
        }
    }

    private fun setOnOrderOptionByOtherReason(isSelected: Boolean){
        when(isSelected){
            true -> {
                orderCancelationnStatus = 3
                mBottomSheetDialog.findViewById<ImageView>(R.id.btn_OtherReason)?.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_checked))
            }
            else ->
                mBottomSheetDialog.findViewById<ImageView>(R.id.btn_OtherReason)?.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_unchecked))
        }
    }


}