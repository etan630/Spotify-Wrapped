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
import com.example.spotify_wrapped.databinding.CardClothesBinding;

import javax.annotation.Nullable;

public class ClothesCard extends BaseCardFragment {
    private CardClothesBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // inflate layout
        binding = CardClothesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // pulling from database
        Artist topArtist = getWrapToDisplay().getArtist(0);
        Artist topArtist2 = getWrapToDisplay().getArtist(1);
        Artist topArtist3 = getWrapToDisplay().getArtist(2);

        String clothes = getWrapToDisplay().getDress();
        String[] clothesArray = clothes.split(", ");

        // binding views to variables
        ImageView artist1Img = binding.artist1Img;
        ImageView artist2Img = binding.artist2Img;
        ImageView artist3Img = binding.artist3Img;

        TextView topArtist1Clothes = binding.topArtist1Clothes;
        TextView topArtist2Clothes = binding.topArtist2Clothes;
        TextView topArtist3Clothes = binding.topArtist3Clothes;

        TextView clothes1 = binding.clothes1;
        TextView clothes2 = binding.clothes2;
        TextView clothes3 = binding.clothes3;

        // setting data
        ImageGenerator.generate(topArtist.getImageLink(), artist1Img, 100, 100);
        ImageGenerator.generate(topArtist2.getImageLink(), artist2Img, 100, 100);
        ImageGenerator.generate(topArtist3.getImageLink(), artist3Img, 100, 100);

        topArtist1Clothes.setText(topArtist.getName());
        topArtist2Clothes.setText(topArtist2.getName());
        topArtist3Clothes.setText(topArtist3.getName());


        if (clothesArray.length < 3) {
            clothes3.setText("Looks like there aren't relating clothes!");
            clothes3.setTextSize(10);
        } else {
            // capitalize the first letter of each clothing trait
            for (int i = 0; i < 3; i++) {
                String firstLetter = clothesArray[i].substring(0, 1).toUpperCase();
                String restOfWord = clothesArray[i].substring(1);
                clothesArray[i] = firstLetter + restOfWord;
            }

            clothes1.setText(clothesArray[0]);
            clothes2.setText(clothesArray[1]);
            clothes3.setText(clothesArray[2]);
        }

        return root;
    }
}
