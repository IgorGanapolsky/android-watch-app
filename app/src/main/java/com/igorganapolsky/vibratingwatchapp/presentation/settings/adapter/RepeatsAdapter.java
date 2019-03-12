package com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igorganapolsky.vibratingwatchapp.R;

public class RepeatsAdapter extends RecyclerView.Adapter<RepeatsAdapter.RepeatsRecyclerViewHolder> {
    private String[] repeats = {
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9"};

    private HolderClickListener holderClickListener;

    public RepeatsAdapter(HolderClickListener holderClickListener) {
        this.holderClickListener = holderClickListener;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public RepeatsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.set_timer_repeats_item, viewGroup, false);
        return new RepeatsRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepeatsRecyclerViewHolder repeatsRecyclerViewHolder, int index) {
        repeatsRecyclerViewHolder.bind(repeats[index], holderClickListener);
    }

    @Override
    public int getItemCount() {
        return repeats.length;
    }

    static class RepeatsRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView tvLabel;

        RepeatsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvLabel = itemView.findViewById(R.id.label);
        }

        void bind(String label, HolderClickListener holderClickListener) {
            tvLabel.setText(label);

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

