package com.example.spotify_wrapped.ui.generate_wrap.cards;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.R;
import com.example.spotify_wrapped.data_collection.Artist;
import com.example.spotify_wrapped.data_collection.Song;
import com.example.spotify_wrapped.databinding.CardGuessTopGameBinding;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * A card for the Guess Top song, artist, and genre game where users input their guesses
 * After submission, the user is told if their answer is right or wrong
 * The user's guess is not case sensitive and does not consider leading white spaces
 */
public class GuessTopGameCard extends BaseCardFragment {
    private CardGuessTopGameBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // to inflate the layout for the card
        binding = CardGuessTopGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Pulling the user's top song, artist, and genres from the wrap
        Song topSong = getWrapToDisplay().getSong(0);
        Artist topArtist = getWrapToDisplay().getArtist(0);
        HashMap<String, Long> genres = getWrapToDisplay().getTopGenres();
        Set<String> genreNames = genres.keySet();
        String topGenreName = "";
        long topGenreFrequency = -1;
        for (String genre : genreNames) {
            if (genres.get(genre) > topGenreFrequency) {
                topGenreName = genre;
                topGenreFrequency = genres.get(genre);
            }
        }

        // Find the views for the user's guesses and correct answers
        EditText topSongInput = root.findViewById(R.id.topSongGuess);
        EditText topGenreInput = root.findViewById(R.id.topGenreGuess);
        EditText topArtistInput = root.findViewById(R.id.topArtistGuess);
        TextView topSongCorrectAnswer = binding.topSongCorrectAnswer;
        TextView topGenreCorrectAnswer = binding.topGenreCorrectAnswer;
        TextView topArtistCorrectAnswer = binding.topArtistCorrectAnswer;
        Button guessButton = root.findViewById(R.id.guessTopButton);

        // set an OnClickListener for the guess button
        String finalTopGenreName = topGenreName;
        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get user's guesses and gets rid of leading white spaces
                String topSongGuessText = topSongInput.getText().toString().trim();
                String topGenreGuessText = topGenreInput.getText().toString().trim();
                String topArtistGuessText = topArtistInput.getText().toString().trim();

                // convert user and actual answer to all lowercase
                String songGuessToLower = topSongGuessText.toLowerCase();
                String genreGuessToLower = topGenreGuessText.toLowerCase();
                String artistGuessToLower = topArtistGuessText.toLowerCase();

                String songToLower = topSong.getName().toLowerCase();
                String genreToLower = finalTopGenreName.toLowerCase();
                String artistToLower = topArtist.getName().toLowerCase();

                // check user's guesses
                boolean topSongCorrect = songGuessToLower.equals(songToLower);
                boolean topGenreCorrect = genreGuessToLower.equals(genreToLower);
                boolean topArtistCorrect = artistGuessToLower.equals(artistToLower);

                // Display the correct answers
                topSongCorrectAnswer.setText("Answer: " + topSong.getName());
                topGenreCorrectAnswer.setText("Answer: " + finalTopGenreName);
                topArtistCorrectAnswer.setText("Answer: " + topArtist.getName());

                int color = Color.parseColor("#006600");
                // Change the text color to green if the guess is correct and red if wrong
                if (topSongCorrect) {
                    topSongCorrectAnswer.setTextColor(color);
                } else {
                    topSongCorrectAnswer.setTextColor(Color.RED);
                }
                if (topGenreCorrect) {
                    topGenreCorrectAnswer.setTextColor(color);
                } else {
                    topGenreCorrectAnswer.setTextColor(Color.RED);
                }
                if (topArtistCorrect) {
                    topArtistCorrectAnswer.setTextColor(color);
                } else {
                    topArtistCorrectAnswer.setTextColor(Color.RED);
                }
            }
        });

        return root;
    }
}