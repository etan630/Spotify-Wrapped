package com.example.spotify_wrapped.ui.generate_wrap.cards;

import androidx.fragment.app.Fragment;

import com.example.spotify_wrapped.data_collection.Wrapped;

public abstract class BaseCardFragment extends Fragment {
    public Wrapped getWrapToDisplay() {
        return getArguments().getSerializable("wrap", Wrapped.class);
    }
}
