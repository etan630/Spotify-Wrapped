package com.example.spotify_wrapped.ui.past_wraps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_wrapped.R;
import com.example.spotify_wrapped.data_collection.ImageGenerator;
import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.databinding.WrappedHistoryCardBinding;

import java.util.List;

public class WrappedListAdapter extends RecyclerView.Adapter<WrappedListAdapter.ViewHolder> {
    private final NavController navController;
    private List<Wrapped> wrappeds;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateView;
        public TextView typeView;
        public ImageView coverView;
        public LinearLayout totalView;

        public ViewHolder(WrappedHistoryCardBinding binding) {
            super(binding.getRoot());

            // Define click listener for the ViewHolder's View

            dateView = binding.dateTextView;
            typeView = binding.wrappedType;
            coverView = binding.albumImg;
            totalView = binding.totalView;
        }

    }

    public WrappedListAdapter(List<Wrapped> wrappeds, NavController navController) {
        this.wrappeds = wrappeds;
        this.navController = navController;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        WrappedHistoryCardBinding binding = WrappedHistoryCardBinding.inflate(layoutInflater, viewGroup, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Wrapped wrapped = wrappeds.get(position);
        String time = wrapped.getTime();
        viewHolder.dateView.setText(time.substring(0, time.indexOf(" ")));
        viewHolder.typeView.setText(wrapped.getTimeFrameString());
        ImageGenerator.generate(wrapped.getSong(0).getImageLink(), viewHolder.coverView, 100, 100);
        viewHolder.totalView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("wrapped", wrapped);

            navController.navigate(R.id.displayPastFragment, bundle);
        });
    }

    @Override
    public int getItemCount() {
        return wrappeds.size();
    }
}
