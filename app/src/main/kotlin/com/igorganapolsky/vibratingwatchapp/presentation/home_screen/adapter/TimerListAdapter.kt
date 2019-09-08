package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.core.TimerDiffCallback
import com.igorganapolsky.vibratingwatchapp.core.TimerTransform
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel
import java.util.*

private class TimerListAdapter : RecyclerView.Adapter<TimerListAdapter.TimerItemViewHolder>() {
    private var data: List<TimerModel>? = null
    private var itemClickListener: OnItemClickListener? = null

    init {
        data = ArrayList()
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data!![position].id.toLong()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): TimerItemViewHolder {
        return TimerItemViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.timer_list_item,
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(timerItemViewHolder: TimerItemViewHolder, index: Int) {
        timerItemViewHolder.bind(data!![index], itemClickListener)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    fun setData(data: List<TimerModel>) {
        val callback = TimerDiffCallback(this.data!!, data)
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

    internal class TimerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivStatus: ImageView = itemView.findViewById(R.id.ivStatus)
        private val icProgress: ImageView = itemView.findViewById(R.id.icProgress)
        private val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        private val tvVibration: TextView = itemView.findViewById(R.id.tvVibration)
        private val tvRepeat: TextView = itemView.findViewById(R.id.tvRepeat)

        fun bind(model: TimerModel, itemClickListener: OnItemClickListener?) {

            if (model.state === TimerModel.State.FINISHED) {
                ivStatus.visibility = View.VISIBLE
                icProgress.visibility = View.INVISIBLE
            } else {
                ivStatus.visibility = View.GONE
                icProgress.visibility = View.VISIBLE
            }

            tvTime.text = TimerTransform.millisToString(model)
            tvVibration.text = String.format(Locale.ENGLISH, "%d", model.vibrationCount)
            tvRepeat.text = String.format(Locale.ENGLISH, "%d", model.repeat)
            itemView.setOnClickListener { view -> itemClickListener!!.onItemClick(model.id) }
        }
    }
}

