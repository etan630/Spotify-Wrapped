package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.databinding.CardLlmBinding;

import javax.annotation.Nullable;

public class LLMCard extends BaseCardFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CardLlmBinding binding = CardLlmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String locationListened = getWrapToDisplay().getLocation();
        TextView llmListenedCity = binding.llmListenedCity;

        llmListenedCity.setText(locationListened);

        return root;

    }
}