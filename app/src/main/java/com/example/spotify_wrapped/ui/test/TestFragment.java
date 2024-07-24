package com.example.spotify_wrapped.ui.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spotify_wrapped.data_collection.ImageGenerator;
import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.data_collection.WrappedGenerator;
import com.example.spotify_wrapped.databinding.FragmentTestBinding;

import java.util.HashMap;
import java.util.Set;

/**
 * Placeholder fragment for navigation
 */
public class TestFragment extends Fragment {
    private String token;
    private String username;
    private String userID;
    private String term = "long_term";
    private FragmentTestBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Intent intent = requireActivity().getIntent();
        token = intent.getStringExtra("token");
        userID = intent.getStringExtra("id");

        username = "placeholder";

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        WrappedGenerator generator = new WrappedGenerator(term, token, getContext());

        TextView songView = binding.TopSong;
        TextView artistView = binding.TopArtist;
        TextView songViewUrl = binding.SongURL;
        TextView artistViewUrl = binding.ArtistURL;
        TextView timeView = binding.TimeFrame;
        TextView topGenre = binding.TopGenre;
        TextView usernameView = binding.Username;
        TextView userIDView = binding.UniqueID;
        TextView locationView = binding.Location;
        TextView hobbiesView = binding.Hobbies;
        TextView dressView = binding.Dress;
        TextView socialView = binding.Social;
        TextView averageDanceabilityView = binding.averageDanceability;
        TextView averageEnergyView = binding.averageEnergy;
        TextView averageValenceView = binding.averageValence;
        TextView averageInstrumentalityView = binding.averageInstrumentality;
        TextView reccomendedArtistNameView = binding.reccomendedArtistName;
        TextView progressView = binding.progessView;
        TextView lyricsvView = binding.lyrics;

        ImageView songImageView = binding.songImage;
        ImageView artistImageView = binding.artistImage;

        Button showElements = binding.showElements;
        showElements.setOnClickListener(view -> {
            Wrapped generatedWrapped = generator.getWrapped();
            ImageGenerator.generate(generatedWrapped.getArtist(0).getImageLink(), artistImageView, 100, 100);
            ImageGenerator.generate(generatedWrapped.getSong(0).getImageLink(), songImageView, 100, 100);
            songView.setText(generatedWrapped.getSong(0).getName());
            songViewUrl.setText(generatedWrapped.getSong(0).getImageLink());
            artistView.setText(generatedWrapped.getArtist(0).getName());
            artistViewUrl.setText(generatedWrapped.getArtist(0).getImageLink());
            timeView.setText(generatedWrapped.getTimeFrameString());
            locationView.setText(generatedWrapped.getLocation());
            hobbiesView.setText(generatedWrapped.getHobbies());
            dressView.setText(generatedWrapped.getDress());
            socialView.setText(generatedWrapped.getSocialGroup());
            averageDanceabilityView.setText(String.format("Danceability Average: %.3f", generatedWrapped.getAverageDanceability()));
            averageEnergyView.setText(String.format("Energy Average: %.3f", generatedWrapped.getAverageEnergy()));
            averageValenceView.setText(String.format("Valence Average: %.3f", generatedWrapped.getAverageValence()));
            averageInstrumentalityView.setText(String.format("Instrumentality Average: %.3f", generatedWrapped.getAverageInstrumentality()));
            reccomendedArtistNameView.setText(generatedWrapped.getReccomendedArtists().toString());
            progressView.setText(String.format("Progress: %.2f", generator.getProgress() * 100));
            lyricsvView.setText(generatedWrapped.getSong(0).getLyric());
            HashMap<String, Long> genres = generatedWrapped.getTopGenres();
            Set<String> genreNames = genres.keySet();
            String topGenreName = "";
            long topGenreFrequency = -1;
            for (String genre : genreNames) {
                if (genres.get(genre) > topGenreFrequency) {
                    topGenreName = genre;
                    topGenreFrequency = genres.get(genre);
                }
            }
            topGenre.setText(topGenreName);
            userIDView.setText(userID);
            usernameView.setText(username);

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}