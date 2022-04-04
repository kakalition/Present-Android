package com.daggery.patternlistpage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daggery.patternlistpage.databinding.TilePatternBinding
import com.daggery.present.domain.entities.BreathPatternItem

class PatternListAdapter(
    private val showModal: (String) -> Unit
) :
    ListAdapter<BreathPatternItem, PatternListAdapter.PatternItemViewHolder>(PatternItemDiffer()) {

    class PatternItemViewHolder(private val binding: TilePatternBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(value: BreathPatternItem) {
            with(binding) {
                textPatternName.text = value.name
                textTempo.text = formatTempo(value)
                textRepetition.text = value.repetitions.toString()
                textDuration.text = getDuration(value)
            }
        }

        private fun formatTempo(value: BreathPatternItem): String {
            var tempo = ""
            with(value) {
                tempo += inhaleDuration
                tempo += "-"
                tempo += holdPostInhaleDuration
                tempo += "-"
                tempo += exhaleDuration
                tempo += "-"
                tempo += holdPostExhaleDuration
            }
            return tempo
        }

        private fun getDuration(value: BreathPatternItem): String {
            var duration = 0
            repeat(value.repetitions) {
                duration += value.inhaleDuration
                duration += value.holdPostInhaleDuration
                duration += value.exhaleDuration
                duration += value.holdPostExhaleDuration
            }
            return duration.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PatternItemViewHolder {
        return PatternItemViewHolder(
            TilePatternBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PatternItemViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            showModal(current.uuid)
        }
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