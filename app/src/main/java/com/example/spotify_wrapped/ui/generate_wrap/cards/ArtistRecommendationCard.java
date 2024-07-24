package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.data_collection.Artist;
import com.example.spotify_wrapped.data_collection.ImageGenerator;
import com.example.spotify_wrapped.databinding.CardArtistRecommendationBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;


public class ArtistRecommendationCard extends BaseCardFragment {
    private CardArtistRecommendationBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CardArtistRecommendationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // getting recommended artists for the card
        List<Artist> recommended = getWrapToDisplay().getReccomendedArtists();
        List<Artist> recommendedList = new ArrayList<>(recommended);
        String recommendedArtists = recommended.toString();
        String[] recArtistArray = recommendedArtists.split(", ");

        // initializing components of the card
        TextView num1 = binding.num1;
        ImageView albumImg1 = binding.albumImg1;
        TextView artistRec1 = binding.artistRec1;

        TextView num2 = binding.num2;
        ImageView albumImg2 = binding.albumImg2;
        TextView artistRec2 = binding.artistRec2;

        TextView num3 = binding.num3;
        ImageView albumImg3 = binding.albumImg3;
        TextView artistRec3 = binding.artistRec3;

        // edge case if there are less than 3 recommended artists
        if (recArtistArray.length < 3) {
            artistRec1.setText("Looks like you don't have any artist recs!");
            artistRec1.setTextSize(7);
            num1.setText("");
            num2.setText("");
            num3.setText("");
            return root;
        }

        // setting text for the artist recommendation
        artistRec1.setText(recArtistArray[0].substring(1));
        artistRec2.setText(recArtistArray[1]);
        artistRec3.setText(recArtistArray[2]);

        // generating album cover for the recommended artists
        ImageGenerator.generate(recommendedList.get(0).getImageLink(), albumImg1, 50, 50);
        ImageGenerator.generate(recommendedList.get(1).getImageLink(), albumImg2, 50, 50);
        ImageGenerator.generate(recommendedList.get(2).getImageLink(), albumImg3, 50, 50);

        return root;
    }
}