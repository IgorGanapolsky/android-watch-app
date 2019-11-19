package com.igorganapolsky.vibratingwatchapp.domain.model

import androidx.recyclerview.widget.DiffUtil

import com.igorganapolsky.vibratingwatchapp.domain.model.model.TimerModel

class TimerDiffCallback(
    private val oldModelList: List<TimerModel>,
    private val newModelList: List<TimerModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldModelList.size
    }

    override fun getNewListSize(): Int {
        return newModelList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModelList[oldItemPosition].id == newModelList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldModelList[oldItemPosition].state === newModelList[newItemPosition].state &&
            oldModelList[oldItemPosition].vibrationCount == newModelList[newItemPosition].vibrationCount &&
            oldModelList[oldItemPosition].hours == newModelList[newItemPosition].hours &&
            oldModelList[oldItemPosition].minutes == newModelList[newItemPosition].minutes &&
            oldModelList[oldItemPosition].seconds == newModelList[newItemPosition].seconds &&
            oldModelList[oldItemPosition].repeat == newModelList[newItemPosition].repeat
    }
}
