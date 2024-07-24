package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.databinding.CardEndWrapBinding;

import javax.annotation.Nullable;


public class EndWrapCard extends BaseCardFragment {
    private CardEndWrapBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CardEndWrapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

}