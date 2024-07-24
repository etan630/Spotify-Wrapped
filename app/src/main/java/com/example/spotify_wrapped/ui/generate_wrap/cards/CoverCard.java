package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.R;

import javax.annotation.Nullable;

public class CoverCard extends BaseCardFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate empty layout
        return inflater.inflate(R.layout.card_cover_page, container, false);
    }
}