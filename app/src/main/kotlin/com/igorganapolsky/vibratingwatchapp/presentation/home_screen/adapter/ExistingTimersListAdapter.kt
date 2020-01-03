package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.databinding.TimerListItemBinding
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerDiffCallback
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerTransform
import com.igorganapolsky.vibratingwatchapp.domain.model.model.TimerModel
import kotlinx.android.extensions.LayoutContainer
import java.util.*

/**
 * Adapter that holds values for items in the list of existing timers screen.
 */
internal class ExistingTimersListAdapter :
    RecyclerView.Adapter<ExistingTimersListAdapter.TimerItemViewHolder>() {

    private var data: List<TimerModel>
    private lateinit var itemClickListener: OnItemClickListener

    init {
        data = listOf()
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLong()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): TimerItemViewHolder {
        val binding = TimerListItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return TimerItemViewHolder(binding)
    }

    override fun onBindViewHolder(timerItemViewHolder: TimerItemViewHolder, index: Int) {
        timerItemViewHolder.bind(data[index], itemClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(data: List<TimerModel>) {
        val callback =
            TimerDiffCallback(this.data, data)
        val result = DiffUtil.calculateDiff(callback)
        this.data = data
        result.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    internal class TimerItemViewHolder(private val binding: TimerListItemBinding) :
        RecyclerView.ViewHolder(binding.root), LayoutContainer {

        override val containerView: View?
            get() = binding.root

        fun bind(model: TimerModel, itemClickListener: OnItemClickListener?) {
            if (model.state === TimerModel.State.FINISHED) {
                binding.statusImageView.visibility = View.VISIBLE
                binding.progressImageView.visibility = View.INVISIBLE
            } else {
                binding.statusImageView.visibility = View.GONE
                binding.progressImageView.visibility = View.VISIBLE
            }

            binding.timeTextView.text = TimerTransform.millisToString(model)
            binding.vibrationTextView.text =
                String.format(Locale.ENGLISH, "%d", model.vibrationCount)
            binding.repeatTextView.text = String.format(Locale.ENGLISH, "%d", model.repeat)
            itemView.setOnClickListener { view -> itemClickListener!!.onItemClick(model.id) }
        }
    }

}
