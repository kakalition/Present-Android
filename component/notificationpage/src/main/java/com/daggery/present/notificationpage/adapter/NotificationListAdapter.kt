package com.daggery.present.notificationpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daggery.present.domain.entities.NotificationItem
import com.daggery.present.notificationpage.databinding.TileNotificationBinding

class NotificationListAdapter :
    ListAdapter<NotificationItem, NotificationListAdapter.NotificationItemViewHolder>(
        NotificationDiffer()
    ) {

    class NotificationItemViewHolder(private val binding: TileNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(value: NotificationItem) {
                binding.textNotificationMessage.text = value.message
                binding.textTime.text = "${value.hour}:${value.minute}"
                binding.switchIsActive.isChecked = value.isActive
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationItemViewHolder {
        return NotificationItemViewHolder(
            TileNotificationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotificationItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}

private class NotificationDiffer : DiffUtil.ItemCallback<NotificationItem>() {

    override fun areItemsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: NotificationItem, newItem: NotificationItem): Boolean {
        return oldItem == newItem
    }
}