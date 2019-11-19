package com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.set_timer_time_fragment.*

internal class RepeatsAdapter(private val holderClickListener: HolderClickListener) :
    RecyclerView.Adapter<RepeatsAdapter.RepeatsRecyclerViewHolder>() {

    private val repeats = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9")

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RepeatsRecyclerViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.set_timer_repeats_item, viewGroup, false)
        return RepeatsRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(
        repeatsRecyclerViewHolder: RepeatsRecyclerViewHolder,
        index: Int
    ) {
        repeatsRecyclerViewHolder.bind(repeats[index], holderClickListener)
    }

    override fun getItemCount(): Int {
        return repeats.size
    }

    internal class RepeatsRecyclerViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(label: String, holderClickListener: HolderClickListener?) {
            textViewLabel.text = label

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

