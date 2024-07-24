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
import com.example.spotify_wrapped.data_collection.Song;
import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.databinding.CardGeneralWrapBinding;

import java.util.HashMap;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Represents the general spotify wrap stats
 * Consists of: Top 3 songs, genres and artists as well as traits of the top song
 */
public class GeneralWrapCard extends BaseCardFragment {
    private CardGeneralWrapBinding binding;

    /**
     * Inflates the card_general_wrap layout and sets the top songs, artists, and genres
     *
     * @param inflater  The LayoutInflater object that can be used to inflate
     *                  any views in the fragment,
     * @param container THe parent view that the fragment's UI should be attached to
     * @param savedInstanceState The saved instance state bundle
     *
     * @return the root view of the fragment
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CardGeneralWrapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Wrapped wrapToDisplay = getWrapToDisplay();

        Song topSong = wrapToDisplay.getSong(0);
        Song topSong2 = wrapToDisplay.getSong(1);
        Song topSong3 = wrapToDisplay.getSong(2);
        Artist topArtist = wrapToDisplay.getArtist(0);
        Artist topArtist2 = wrapToDisplay.getArtist(1);
        Artist topArtist3 = wrapToDisplay.getArtist(2);
        HashMap<String, Long> genres = wrapToDisplay.getTopGenres();

        Set<String> genreNames = genres.keySet();
        String topGenreName = "";
        String topGenreName2 = "";
        String topGenreName3 = "";
        long topGenreFrequency = -1;
        long topGenreFrequency2 = -1;
        long topGenreFrequency3 = -1;
        for (String genre : genreNames) {
            if (genres.get(genre) > topGenreFrequency) {
                topGenreName3 = topGenreName2;
                topGenreName2 = topGenreName;
                topGenreName = genre;
                topGenreFrequency3 = topGenreFrequency2;
                topGenreFrequency2 = topGenreFrequency;
                topGenreFrequency = genres.get(genre);
                continue;
            }
            if (genres.get(genre) > topGenreFrequency2) {
                topGenreName3 = topGenreName2;
                topGenreName2 = genre;
                topGenreFrequency3 = topGenreFrequency2;
                topGenreFrequency2 = genres.get(genre);
                continue;
            }
            if (genres.get(genre) > topGenreFrequency3) {
                topGenreName3 = genre;
                topGenreFrequency3 = genres.get(genre);
                continue;
            }
        }

        TextView topSongText = binding.topSongText;
        TextView topSongArtist = binding.topSongArtist;
        ImageView topSongImage = binding.topSongImg;

        TextView topSongText2 = binding.song2Text;
        TextView topSongArtist2 = binding.song2Artist;
        ImageView topSongImage2 = binding.songImg2;

        TextView topSongText3 = binding.song3Text;
        TextView topSongArtist3 = binding.song3Artist;
        ImageView topSongImage3 = binding.songImg3;
        //TODO readd back the danceability and other song information bars, they were deleted for
        // TODO making the code work
        /**
         * ProgressBar danceabilityProgress = binding.danceabilityProgress;
         *         ProgressBar energyProgress = binding.energyProgress;
         *         ProgressBar valenceProgress = binding.valenceProgress;
         *
         *         TextView danceabilityValue = binding.danceabilityValue;
         *         TextView energyValue = binding.energyValue;
         *         TextView valenceValue = binding.valenceValue;
         */
        TextView topArtistText = binding.topArtist1;
        TextView topArtistText2 = binding.topArtist2;
        TextView topArtistText3 = binding.topArtist3;

        TextView topGenreText = binding.topGenre1;
        TextView topGenreText2 = binding.topGenre2;
        TextView topGenreText3 = binding.topGenre3;

        //TODO change dimensions to something based on Wrapped size not direct values like 100 or 80
        topSongText.setText(topSong.getName());
        topSongArtist.setText(topSong.getArtists().get(0).getName());
        ImageGenerator.generate(topSong.getImageLink(), topSongImage, 100, 100);
        topSongText2.setText(topSong2.getName());
        topSongArtist2.setText(topSong2.getArtists().get(0).getName());
        ImageGenerator.generate(topSong2.getImageLink(), topSongImage2, 80, 80);
        topSongText3.setText(topSong3.getName());
        topSongArtist3.setText(topSong3.getArtists().get(0).getName());
        ImageGenerator.generate(topSong3.getImageLink(), topSongImage3, 80, 80);
/**
        danceabilityProgress.setProgress((int) (topSong.getDanceability() * 100));
        energyProgress.setProgress((int) (topSong.getEnergy() * 100));
        valenceProgress.setProgress((int) (topSong.getValence() * 100));

        danceabilityValue.setText(Integer.toString((int) (topSong.getDanceability() * 100)));
        energyValue.setText(Integer.toString((int) (topSong.getEnergy() * 100)));
        valenceValue.setText(Integer.toString((int) (topSong.getValence() * 100)));
 */

        topArtistText.setText(topArtist.getName());
        topArtistText2.setText(topArtist2.getName());
        topArtistText3.setText(topArtist3.getName());

        topGenreText.setText(topGenreName);
        topGenreText2.setText(topGenreName2);
        topGenreText3.setText(topGenreName3);

        //inflate empty layout
        return root;
    }
}