package com.hossam.tawsel.feature_orders.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.hossam.tawsel.core.TempData
import com.hossam.tawsel.core.onClick
import com.hossam.tawsel.databinding.ItemExpiredOrderBinding
import com.hossam.tawsel.databinding.ItemInstockOrderBinding
import com.hossam.tawsel.databinding.ItemStartedOrderBinding
import com.hossam.tawsel.feature_home.domain.model.Cancel
import com.hossam.tawsel.feature_orders.domain.model.DataDto
import javax.inject.Inject

class OrdersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject
    lateinit var glide: RequestManager //TODO Glide crash in this recycler adapter but in other classes works fine
    private var orders: List<DataDto> = emptyList()
    private val VIEW_TYPE_INSTOCK_ORDER = 1
    private val VIEW_TYPE_STARTED_ORDER = 2
    private val VIEW_TYPE_EXPIRED_ORDER = 3

    var onStartItemClick: ((Int)-> Unit)? = null
    var onDetailsItemClick: ((Int)-> Unit)? = null
    var onFinishItemClick: ((Int)-> Unit)? = null
    var onCancelItemClick: ((Cancel)-> Unit)? = null

    fun setOrdersList(orders: List<DataDto>){
        this.orders = orders
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val startedVH by lazy { ItemStartedOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        val inStockVH by lazy { ItemInstockOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        val expiredVH by lazy { ItemExpiredOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false) }

        return when(viewType){
            VIEW_TYPE_INSTOCK_ORDER -> InStockOrderVH(inStockVH)
            VIEW_TYPE_STARTED_ORDER -> StartedOrderVH(startedVH)
            VIEW_TYPE_EXPIRED_ORDER -> ExpiredOrderVH(expiredVH)
            else -> { throw Exception("Can't decide the Recycler View Holder Class") }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is InStockOrderVH -> { holder.bind(position) }
            is StartedOrderVH -> { holder.bind(position) }
            is ExpiredOrderVH -> { holder.bind(position) }
        }
    }

    override fun getItemCount(): Int = orders.size

    inner class InStockOrderVH(val binding: ItemInstockOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val getItemPos = orders[position]
            binding.apply {
//                glide.load(TempData.RESTAURANT_PIC)
//                    .into(ivRestaurantLogo)

                tvRestaurantName.text = getItemPos.client
                tvCurrentLocation2.text = getItemPos.address
                tvCurrentLocation3.text = getItemPos.address
                tvTime.text = getItemPos.date + " " + getItemPos.start
                tvPaymentType.text = "Cash"
                btnShowOrderDetails.onClick {
                    onDetailsItemClick?.invoke(getItemPos.id)
                }

                btnStartOrder.onClick {
                    onStartItemClick?.invoke(getItemPos.id)
                    notifyDataSetChanged()
                }

                btnCancelOrder.onClick {
                    onCancelItemClick?.invoke(Cancel(getItemPos.id, "العميل لغى الطلب"))
                    notifyDataSetChanged()
                }
            }
        }
    }

    inner class StartedOrderVH(val binding: ItemStartedOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val getItemPos = orders[position]
            binding.apply {
//                glide.load(TempData.RESTAURANT_PIC)
//                    .into(ivRestaurantLogo)

                tvRestaurantName.text = getItemPos.client
                tvCurrentLocation2.text = getItemPos.address
                tvCurrentLocation3.text = getItemPos.address
                tvTime.text = getItemPos.date + " " + getItemPos.start
                tvPaymentType.text = "Cash"
                btnShowOrderDetails.onClick {
                    onDetailsItemClick?.invoke(getItemPos.id)
                }

                btnFinishOrder.onClick {
                    onFinishItemClick?.invoke(getItemPos.id)
                    notifyDataSetChanged()
                }

                btnCancelOrder.onClick {
                    onCancelItemClick?.invoke(Cancel(getItemPos.id, "العميل لغى الطلب"))
                    notifyDataSetChanged()
                }
            }
        }
    }

    inner class ExpiredOrderVH(val binding: ItemExpiredOrderBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val getItemPos = orders[position]
            binding.apply {
//                glide.load(TempData.RESTAURANT_PIC)
//                    .into(ivRestaurantLogo)

                tvRestaurantName.text = getItemPos.client
                tvCurrentLocation2.text = getItemPos.address
                tvStartPoint.text = getItemPos.address
                tvEndPoint.text = getItemPos.address
                tvTime.text = getItemPos.date + " " + getItemPos.start
                tvOrderStatus.text = "Cash"

                btnShowOrderDetails.onClick {
                    onDetailsItemClick?.invoke(getItemPos.id)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(orders[position].status.lowercase()){
            "in stock" -> VIEW_TYPE_INSTOCK_ORDER
            "started" -> VIEW_TYPE_STARTED_ORDER
            "cancelled" -> VIEW_TYPE_EXPIRED_ORDER
            "completed" -> VIEW_TYPE_EXPIRED_ORDER
            else -> { throw Exception("Can't decide the order view type") }
        }
    }
}