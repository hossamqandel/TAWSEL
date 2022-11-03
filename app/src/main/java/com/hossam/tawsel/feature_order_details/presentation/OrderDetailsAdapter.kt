package com.hossam.tawsel.feature_order_details.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hossam.tawsel.R
import com.hossam.tawsel.feature_order_details.data.remote.dto.OrderDetailsDto

class OrderDetailsAdapter : RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {

    private lateinit var data: OrderDetailsDto

    fun setOrderDetailsData(data: OrderDetailsDto){
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_detail, parent, false)
        return OrderDetailsViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
        val getItem = data.data.items[position]

        holder.productName.text = "${getItem.item} ج.م"
        holder.productPrice.text = "${getItem.price} ج.م"


        if (position == 0){
            holder.orderDetailsMessage.visibility = View.VISIBLE
            holder.products.visibility = View.VISIBLE
        } else {
            holder.orderDetailsMessage.visibility = View.GONE
            holder.products.visibility = View.GONE
        }

        if (position == data.data.items.lastIndex){
            holder.fullCostValue.text = "${data.data.total} ج.م"
            holder.deliveryCostValue.text = "${data.data.shipping} ج.م"

            holder.fullCost.visibility = View.VISIBLE
            holder.deliveryCost.visibility = View.VISIBLE
            holder.deliveryCostValue.visibility = View.VISIBLE
            holder.fullCostValue.visibility = View.VISIBLE
            holder.line.visibility = View.VISIBLE

        }

//         else {
//             holder.clientName.text = clientData.client
//             holder.clientAddress.text = clientData.address_details
//             holder.clientPaymentType.text = "Cash"
//             holder.clientPhone.text = clientData.phone
//         }
    }

    override fun getItemCount(): Int = data.data.items.size

    inner class OrderDetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val productName: TextView by lazy { itemView.findViewById(R.id.tv_orderName) }
        val productPrice: TextView by lazy { itemView.findViewById(R.id.tv_orderCost) }
        val fullCost: TextView by lazy { itemView.findViewById(R.id.tv_fullCost) }
        val deliveryCost: TextView by lazy { itemView.findViewById(R.id.tv_deliveryCost) }
        val deliveryCostValue: TextView by lazy { itemView.findViewById(R.id.tv_ShippingCostValue) }
        val fullCostValue: TextView by lazy { itemView.findViewById(R.id.tv_fullCostValue) }

        val orderDetailsMessage: View by lazy { itemView.findViewById(R.id.textView13) }
        val products: View by lazy { itemView.findViewById(R.id.tv_products) }
        val line: View by lazy { itemView.findViewById(R.id.line) }

    }

}