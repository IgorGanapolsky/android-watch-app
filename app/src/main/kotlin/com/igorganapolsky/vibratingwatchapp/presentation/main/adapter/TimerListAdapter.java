package com.igorganapolsky.vibratingwatchapp.presentation.main.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.util.TimerDiffCallback;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimerListAdapter extends RecyclerView.Adapter<TimerListAdapter.TimerItemViewHolder> {

    private List<TimerModel> data;
    private OnItemClickListener itemClickListener;

    public TimerListAdapter() {
        data = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    @NonNull
    @Override
    public TimerItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        return new TimerItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timer_list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimerItemViewHolder timerItemViewHolder, int index) {
        timerItemViewHolder.bind(data.get(index), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<TimerModel> data) {
        TimerDiffCallback callback = new TimerDiffCallback(this.data, data);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        this.data = data;
        result.dispatchUpdatesTo(this);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int id);
    }

    static class TimerItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivStatus;
        private final ImageView icProgress;
        private final TextView tvTime;
        private final TextView tvVibration;
        private final TextView tvRepeat;

        TimerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivStatus = itemView.findViewById(R.id.ivStatus);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvVibration = itemView.findViewById(R.id.tvVibration);
            tvRepeat = itemView.findViewById(R.id.tvRepeat);
            icProgress = itemView.findViewById(R.id.icProgress);
        }

        void bind(TimerModel model, OnItemClickListener itemClickListener) {

            if (model.getState() == TimerModel.State.FINISHED) {
                ivStatus.setVisibility(View.VISIBLE);
                icProgress.setVisibility(View.INVISIBLE);
            } else {
                ivStatus.setVisibility(View.GONE);
                icProgress.setVisibility(View.VISIBLE);
            }

            tvTime.setText(TimerTransform.millisToString(model));
            tvVibration.setText(String.format(Locale.ENGLISH, "%d", model.getVibrationCount()));
            tvRepeat.setText(String.format(Locale.ENGLISH, "%d", model.getRepeat()));
            itemView.setOnClickListener(view -> itemClickListener.onItemClick(model.getId()));
        }
    }
}

