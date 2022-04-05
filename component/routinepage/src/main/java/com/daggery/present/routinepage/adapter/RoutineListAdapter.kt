package com.daggery.present.routinepage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daggery.present.domain.entities.RoutineItem
import com.daggery.present.routinepage.databinding.TileRoutineBinding

// TODO: Add isActive to RoutineItem
class RoutineListAdapter : ListAdapter<RoutineItem, RoutineListAdapter.RoutineItemViewHolder>(RoutineItemDiffer()) {

    class RoutineItemViewHolder(private val binding: TileRoutineBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(value: RoutineItem) {
                binding.textBreathName.text = value.name
                binding.textRoutineTime.text = "${value.hour}:${value.minute}"
                var repeatEvery = ""
                value.repeatEvery.forEach {
                    repeatEvery += "$it, "
                }
                binding.textRoutineDays.text = repeatEvery.dropLast(2)
                binding.switchIsActive.isChecked = value.isActive
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : RoutineItemViewHolder {
        return RoutineItemViewHolder(TileRoutineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: RoutineItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}

private class RoutineItemDiffer : DiffUtil.ItemCallback<RoutineItem>() {
    override fun areItemsTheSame(oldItem: RoutineItem, newItem: RoutineItem): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: RoutineItem, newItem: RoutineItem): Boolean {
        return oldItem == newItem
    }

}