package com.example.spotify_wrapped.data_collection;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Song Generator class that takes in a Song object and adds all the audio features to the song
 * through spotify requests
 */
public class SongGenerator {
    private String token;
    private int progress;
    private OkHttpClient mOkHttpClient;

    /**
     * Song Generator Constructor
     * @param token spotify token used for api requests
     * @param mOkHttpClient http client to make those requests
     */
    public SongGenerator(String token, OkHttpClient mOkHttpClient) {
        progress = 0;
        this.token = token;
        this.mOkHttpClient = mOkHttpClient;
    }

    /**
     * adds song detail through a spotify api request
     * @param song song that we want to add the song features
     */
    public void addSongDetail(Song song) {
        String id = song.getId();
        final Request requestTracks = new Request.Builder()
                .url("https://api.spotify.com/v1/audio-features/" + id)
                .addHeader("Authorization", "Bearer " + token)
                .build();
        Call mCallTracks = mOkHttpClient.newCall(requestTracks);
        mCallTracks.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    Log.d("JSON", jsonObject.toString());
                    song.setDanceability(jsonObject.getDouble("danceability"));
                    song.setEnergy(jsonObject.getDouble("energy"));
                    song.setValence(jsonObject.getDouble("valence"));
                    song.setInstrumentality(jsonObject.getDouble("instrumentalness"));
                    progress++;
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                }
            }
        });
    }

    /**
     * generator that loops through a list of songs and adds right detail
     * @param songs
     */
    public void generate(List<Song> songs) {
        for (Song song: songs) {
            addSongDetail(song);
        }
    }

    /**
     * getter for the progress completed
     * @return
     */

    public int getProgress() {
        return progress;
    }
}
