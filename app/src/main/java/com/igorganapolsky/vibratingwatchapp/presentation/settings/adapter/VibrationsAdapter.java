package com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;

import java.util.List;
import java.util.Locale;

public class VibrationsAdapter extends RecyclerView.Adapter<VibrationsAdapter.VibrationsRecyclerViewHolder> {

    private final String[] vibTitles;
    private final String[] timeTitles;

    private HolderClickListener holderClickListener;
    private List<BuzzSetup> buzzList;

    public VibrationsAdapter(HolderClickListener holderClickListener, String[] vibTitles, String[] timeTitles) {
        this.holderClickListener = holderClickListener;
        this.vibTitles = vibTitles;
        this.timeTitles = timeTitles;
        setHasStableIds(true);
    }

    public void setItems(List<BuzzSetup> newList) {
        buzzList = newList;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public VibrationsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.set_timer_vibrations_line, viewGroup, false);
        return new VibrationsRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VibrationsRecyclerViewHolder vibrationsRecyclerViewHolder, int index) {
        vibrationsRecyclerViewHolder.bind(
            buzzList.get(index),
            vibTitles, timeTitles,
            index,
            holderClickListener);
    }

    @Override
    public int getItemCount() {
        return buzzList == null ? 0 : buzzList.size();
    }

    static class VibrationsRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvIndex;
        private final TextView buzzTitle;

        VibrationsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.index);
            buzzTitle = itemView.findViewById(R.id.buzzTitle);
        }

        //TODO (think about better adapter pattern usages)
        void bind(BuzzSetup buzz, String[] vibTitles, String[] timeTitles, int index, HolderClickListener holderClickListener) {

            String buzzText = vibTitles[index];
            String timeText = timeTitles[index];
            String totalString = String.format(Locale.getDefault(), "%s - %s", buzzText, timeText);

            tvIndex.setText(String.valueOf(index + 1));
            buzzTitle.setText(totalString);

            if (holderClickListener != null) {
                itemView.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        holderClickListener.onHolderClick(position);
                    }
                });
            }
        }
    }
}

