package com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class VibrationsAdapter extends RecyclerView.Adapter<VibrationsAdapter.VibrationsRecyclerViewHolder> {

    private static final int DEFAULT_POSITION = 0;

    private final String[] vibTitles;
    private final String[] timeTitles;

    private final HolderClickListener holderClickListener;
    private final List<BuzzSetup> buzzList;

    public VibrationsAdapter(HolderClickListener holderClickListener, String[] vibTitles, String[] timeTitles) {
        this.holderClickListener = holderClickListener;
        this.vibTitles = vibTitles;
        this.timeTitles = timeTitles;
        buzzList = initBuzzList();
        setHasStableIds(true);
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

    /**
     * Initial method for defining all {@link BuzzSetup}
     *
     * @return setup list;
     */
    private List<BuzzSetup> initBuzzList() {
        List<BuzzSetup> setupList = new ArrayList<>(4);
        setupList.add(new BuzzSetup(BuzzSetup.Type.SHORT, 1, 5));
        setupList.add(new BuzzSetup(BuzzSetup.Type.SHORT, 3, 3));
        setupList.add(new BuzzSetup(BuzzSetup.Type.SHORT, 5, 5));
        setupList.add(new BuzzSetup(BuzzSetup.Type.LONG, 1, 20));
        return setupList;
    }

    /**
     * Returns concrete {@link BuzzSetup} based on position.
     *
     * @param position position of {@link RecyclerView.ViewHolder} in list;
     * @return concrete setup or first element in list, if setup wasn't found;
     */
    public BuzzSetup getBuzzByPosition(int position) {
        try {
            return buzzList.get(position);
        } catch (IndexOutOfBoundsException exp) {
            return buzzList.get(DEFAULT_POSITION);
        }
    }

    /**
     * Returns position o {@link BuzzSetup} in list;
     *
     * @param setup to define position;
     * @return position of {@link BuzzSetup} or {@link VibrationsAdapter#DEFAULT_POSITION}
     */
    public int getPosition(BuzzSetup setup) {
        try {
            return buzzList.indexOf(setup);
        } catch (IndexOutOfBoundsException exp) {
            return DEFAULT_POSITION;
        }
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

