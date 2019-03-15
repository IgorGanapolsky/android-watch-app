package com.igorganapolsky.vibratingwatchapp.core.util;

import android.support.v7.util.DiffUtil;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.List;

public class TimerDiffCallback extends DiffUtil.Callback {

    private final List<TimerModel> oldModelList;
    private final List<TimerModel> newModelList;

    public TimerDiffCallback(List<TimerModel> oldModelList, List<TimerModel> newModelList) {
        this.oldModelList = oldModelList;
        this.newModelList = newModelList;
    }

    @Override
    public int getOldListSize() {
        return oldModelList.size();
    }

    @Override
    public int getNewListSize() {
        return newModelList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldModelList.get(oldItemPosition).getId() == newModelList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldModelList.get(oldItemPosition).getState() == newModelList.get(newItemPosition).getState() &&
            oldModelList.get(oldItemPosition).getBuzzCount() == newModelList.get(newItemPosition).getBuzzCount() &&
            oldModelList.get(oldItemPosition).getHours() == newModelList.get(newItemPosition).getHours() &&
            oldModelList.get(oldItemPosition).getMinutes() == newModelList.get(newItemPosition).getMinutes() &&
            oldModelList.get(oldItemPosition).getSeconds() == newModelList.get(newItemPosition).getSeconds() &&
            oldModelList.get(oldItemPosition).getRepeat() == newModelList.get(newItemPosition).getRepeat();
    }
}
