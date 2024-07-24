package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.R;
import com.example.spotify_wrapped.data_collection.Artist;
import com.example.spotify_wrapped.data_collection.ImageGenerator;
import com.example.spotify_wrapped.databinding.CardHobbiesBinding;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class HobbiesCard extends BaseCardFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CardHobbiesBinding binding = CardHobbiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // pulling from database
        Artist topArtist = getWrapToDisplay().getArtist(0);
        Artist topArtist2 = getWrapToDisplay().getArtist(1);
        Artist topArtist3 = getWrapToDisplay().getArtist(2);

        String hobbies = getWrapToDisplay().getHobbies();
        String[] hobbiesArray = hobbies.split(", ");

        // binding
        ImageView artist1ImgHobbies = binding.artist1ImgHobbies;
        ImageView artist2ImgHobbies = binding.artist2ImgHobbies;
        ImageView artist3ImgHobbies = binding.artist3ImgHobbies;

        TextView topArtist1Hobbies = binding.topArtist1Hobbies;
        TextView topArtist2Hobbies = binding.topArtist2Hobbies;
        TextView topArtist3Hobbies = binding.topArtist3Hobbies;

        TextView hobby1 = binding.hobby1;
        TextView hobby2 = binding.hobby2;
        TextView hobby3 = binding.hobby3;

        ImageView hobby1Img = binding.hobby1Img;
        ImageView hobby2Img = binding.hobby2Img;
        ImageView hobby3Img = binding.hobby3Img;

        // set data for the views
        ImageGenerator.generate(topArtist.getImageLink(), artist1ImgHobbies, 100, 100);
        ImageGenerator.generate(topArtist2.getImageLink(), artist2ImgHobbies, 100, 100);
        ImageGenerator.generate(topArtist3.getImageLink(), artist3ImgHobbies, 100, 100);

        topArtist1Hobbies.setText(topArtist.getName());
        topArtist2Hobbies.setText(topArtist2.getName());
        topArtist3Hobbies.setText(topArtist3.getName());

        if (hobbiesArray.length < 3) {
            hobby1.setText("Oops...");
            hobby2.setText("You seem to have...");
            hobby3.setText("No hobbies??");
        } else {
            String[] hobby = new String[3];
            for (int i = 0; i < 3; i++) {
                String firstLetter = hobbiesArray[i].substring(0, 1).toUpperCase();
                String restOfWord = hobbiesArray[i].substring(1);
                hobby[i] = firstLetter + restOfWord;
            }

            hobby1.setText(hobby[0]);
            hobby2.setText(hobby[1]);
            hobby3.setText(hobby[2]);

            setHobbyImageAndVisibility(hobby1Img, hobbiesArray[0]);
            setHobbyImageAndVisibility(hobby2Img, hobbiesArray[1]);
            setHobbyImageAndVisibility(hobby3Img, hobbiesArray[2]);
        }



        return root;
    }

    private static final Map<String, Integer> hobbyImageMap = new HashMap<>();
    static {
        hobbyImageMap.put("playing instruments", R.drawable.playing_instruments);
        hobbyImageMap.put("art", R.drawable.art);
        hobbyImageMap.put("cooking", R.drawable.cooking);
        hobbyImageMap.put("baking", R.drawable.baking);
        hobbyImageMap.put("crocheting", R.drawable.crocheting);
        hobbyImageMap.put("photography", R.drawable.photography);
        hobbyImageMap.put("reading", R.drawable.reading);
        hobbyImageMap.put("singing", R.drawable.singing);
        hobbyImageMap.put("sports", R.drawable.sports);
        hobbyImageMap.put("video games", R.drawable.video_games);
    }

    private int getHobbyImageResource(String hobby) {
        Integer imageResource = hobbyImageMap.get(hobby);
        return imageResource != null ? imageResource : 0;
    }

    private void setHobbyImageAndVisibility(ImageView imageView, String hobby) {
        int imageResource = getHobbyImageResource(hobby);
        if (imageResource != 0) {
            imageView.setImageResource(imageResource);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
