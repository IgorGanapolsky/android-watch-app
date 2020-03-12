package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.common.TimerDiffCallback
import com.igorganapolsky.vibratingwatchapp.common.TimerTransform
import com.igorganapolsky.vibratingwatchapp.databinding.ItemTimerListBinding
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel
import java.util.*
import kotlin.reflect.KFunction0

/**
 * Adapter that holds values for items in the list of existing timers screen.
 */
internal class TimersListAdapter :
    RecyclerView.Adapter<TimersListAdapter.TimerItemViewHolder>() {

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
        val binding = ItemTimerListBinding.inflate(
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
            TimerDiffCallback(
                this.data,
                data
            )
        val result = DiffUtil.calculateDiff(callback)
        this.data = data
        result.dispatchUpdatesTo(this)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    interface OnItemClickListener {
        // TODO: Igor - add enum/sealed class of possible click events on individual elements
        fun renderViewState(viewStateAction: TimersListAction): KFunction0<Unit>
    }

    internal class TimerItemViewHolder(private val binding: ItemTimerListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: TimerModel, itemClickListener: OnItemClickListener) {
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

            // TODO: Igor - set individual click listeners on items in the row
//            binding.statusImageView.setOnClickListener()

//            itemView.setOnClickListener { view -> itemClickListener.onItemClick(model.id) }
        }
    }

}
