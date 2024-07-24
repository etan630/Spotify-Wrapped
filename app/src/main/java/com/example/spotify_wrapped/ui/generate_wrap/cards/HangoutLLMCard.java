package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.databinding.CardHangoutLlmBinding;

import javax.annotation.Nullable;


public class HangoutLLMCard extends BaseCardFragment {
    private CardHangoutLlmBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflates the view
        binding = CardHangoutLlmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // get social group data
        String friend1 = getWrapToDisplay().getSocialGroup();
        String[] friendTraits = friend1.split(", ");

        // initialize the TextViews that will display the traits
        TextView friendTrait1 = binding.friendTrait1;
        TextView friendTrait2 = binding.friendTrait2;
        TextView friendTrait3 = binding.friendTrait3;

        // if there are less than 3 friend traits, display a default message
        if (friendTraits.length < 3) {
            friendTrait1.setText("We can't seem to tell");
            friendTrait1.setTextSize(14);

            friendTrait2.setText("what friends you would");
            friendTrait2.setTextSize(14);

            friendTrait3.setText("hang out with at the moment...");
            friendTrait3.setTextSize(14);
        } else {
            // capitalize the first letter of each friend trait
            for (int i = 0; i < 3; i++) {
                String firstLetter = friendTraits[i].substring(0, 1).toUpperCase();
                String restOfWord = friendTraits[i].substring(1);
                friendTraits[i] = firstLetter + restOfWord;
            }

            // set the text of the textviews to the corresponding friend trait
            friendTrait1.setText(friendTraits[0]);
            friendTrait2.setText(friendTraits[1]);
            friendTrait3.setText(friendTraits[2]);
        }
        return root;
    }
}