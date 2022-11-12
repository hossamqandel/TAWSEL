package com.hossam.tawsel.feature_notification.presntation.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hossam.tawsel.R
import com.hossam.tawsel.core.Const.NULL_TEXT_FORM
import com.hossam.tawsel.databinding.ItemNotificationBinding
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDataDto
import com.hossam.tawsel.feature_notification.data.remote.dto.NotificationsDto

class NotificationsAdapter : RecyclerView.Adapter<NotificationsAdapter.NotificationsVH>() {

    companion object {
        private val WALLET by lazy { "wallet".lowercase() }
        private val NEW_ORDER by lazy { "New Order".lowercase() }
        private val REMINDER by lazy { "Reminder".lowercase() }
        private val UN_READ_MESSAGE by lazy { "UnRead " }
    }


    private var onNotificationClicked: ((Int) -> Unit)? = null

    private var mItems = emptyList<NotificationsDataDto>()

    fun setAdapterData(notifications: List<NotificationsDataDto>){
        this.mItems = notifications
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsVH {
        val mBindingVH by lazy { ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false) }
        return NotificationsVH(mBindingVH)
    }

    override fun onBindViewHolder(holder: NotificationsVH, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = mItems.size

    inner class NotificationsVH(val binding: ItemNotificationBinding): RecyclerView.ViewHolder(binding.root){

        //TODO make a function that convert readAt to readable value

        fun bind(position: Int){
            val getItem = mItems[position]
            handleWhichDrawableShouldShow(position)
            handleShowHideReminderText(position)
            binding.apply {
                tvNotificationTitle.text = getItem.body
                tvNotificationTime.text = if (getItem.read_at == NULL_TEXT_FORM) UN_READ_MESSAGE else getItem.read_at
            }


        }

        private fun handleWhichDrawableShouldShow(position: Int){
            binding.apply {
                when(mItems[position].title.lowercase()){
                    WALLET -> { ivNotificationType.setImageResource(R.drawable.ic_green_check_mark) }
                    NEW_ORDER -> { ivNotificationType.setImageResource(R.drawable.ic_stop_watch_check_mark) }
                    REMINDER -> { ivNotificationType.setImageResource(R.drawable.ic_reminder_check_mark) }
                }
            }
        }
        private fun handleShowHideReminderText(position: Int){
            binding.apply {
                if (mItems[position].title == REMINDER){
                    tvRemind.isVisible = true
                    return
                }
                tvRemind.isVisible = false
            }
        }
    }
}