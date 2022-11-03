package com.hossam.tawsel.feature_order_details.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hossam.tawsel.R

class ClientInfoAdapter : RecyclerView.Adapter<ClientInfoAdapter.ClientInfoViewHolder>() {

    private var data: List<ClientInfo> = emptyList()

    fun setClientInfo(data: List<ClientInfo>){
        this.data = data
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_client_info, parent, false)
        return ClientInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientInfoViewHolder, position: Int) {
        val get = data.get(position)

        holder.clientTitle.text = get.title
        holder.clientData.text = get.value
        when(position){
            0 -> { holder.clientMainTitle.visibility = View.VISIBLE }
            else -> { }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ClientInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val clientMainTitle: TextView by lazy { itemView.findViewById(R.id.tv_clientInfo) }
        val clientTitle: TextView by lazy { itemView.findViewById(R.id.tv_ClientTitle) }
        val clientData: TextView by lazy { itemView.findViewById(R.id.tv_clientData) }

        init {

        }
    }
}

data class ClientInfo(
    val title: String,
    val value: String
)


