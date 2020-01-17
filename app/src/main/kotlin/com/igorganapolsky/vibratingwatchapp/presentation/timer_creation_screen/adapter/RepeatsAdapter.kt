package com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.databinding.SetTimerRepeatsItemBinding
import kotlinx.android.extensions.LayoutContainer

/**
 * Adapter that holds values for items in the list of repeating options for a new timer screen.
 */
internal class RepeatsAdapter(private val holderClickListener: IHolderClickListener) :
    RecyclerView.Adapter<RepeatsAdapter.RepeatsRecyclerViewHolder>() {

    private val data = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RepeatsRecyclerViewHolder {
        val binding = SetTimerRepeatsItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return RepeatsRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(
        repeatsRecyclerViewHolder: RepeatsRecyclerViewHolder,
        index: Int
    ) {
        repeatsRecyclerViewHolder.bind(data[index], holderClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    internal class RepeatsRecyclerViewHolder(private val binding: SetTimerRepeatsItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = binding.root

        fun bind(label: String, holderClickListener: IHolderClickListener?) {
            binding.repeatsTextView.text = label

            if (holderClickListener != null) {
                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        holderClickListener.onHolderItemClick(position)
                    }
                }
            }
        }
    }

}
