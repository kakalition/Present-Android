package com.daggery.patternlistpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daggery.patternlistpage.databinding.TilePatternBinding
import com.daggery.present.domain.entities.BreathPatternItem

class PatternListAdapter :
    ListAdapter<BreathPatternItem, PatternListAdapter.PatternItemViewHolder>(PatternItemDiffer()) {

    class PatternItemViewHolder(private val binding: TilePatternBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(value: BreathPatternItem) {
            with(binding) {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternItemViewHolder {
        return PatternItemViewHolder(
            TilePatternBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatternItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }
}

private class PatternItemDiffer : DiffUtil.ItemCallback<BreathPatternItem>() {
    override fun areItemsTheSame(
        oldItem: BreathPatternItem,
        newItem: BreathPatternItem
    ): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(
        oldItem: BreathPatternItem,
        newItem: BreathPatternItem
    ): Boolean {
        return oldItem == newItem
    }
}