package com.example.spotify_wrapped.data_collection;


import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Wrapped Generator class, used to create a Wrapped object and fill it with appropiate
 * information, calls upon other generators to add more detail
 */
public class WrappedGenerator {

    private String timeFrame;
    private String token;
    private Context context;
    private List<Artist> topArtists = new ArrayList<>();
    private List<Song> topSongs = new ArrayList<>();
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private Call mCallArtists;
    private Call mCallTracks;
    private int progressCounter;
    public final static int totalCalls = 7;
    private Wrapped generatedWrapped;
    private LLMGenerator llmGenerator = new LLMGenerator();
    private SongGenerator songGenerator;
    private ArtistReccomendationGenerator artistReccomendationGenerator;

    /**
     * constructor for wrapped generator
     * @param timeFrame time frame of wrapped
     * @param token spotify token
     * @param context context of application
     */
    public WrappedGenerator(String timeFrame, String token, Context context) {
        regenerate(timeFrame, token, context);
    }

    /**
     * starts generation process with given information
     * @param timeFrame time frame of wrapped
     * @param token spotify token
     * @param context context of application
     */
    public void regenerate(String timeFrame, String token, Context context) {
        this.timeFrame = timeFrame;
        this.token = token;
        progressCounter = 0;
        this.context = context;
        createWrapped();
        songGenerator = new SongGenerator(token, mOkHttpClient);
        artistReccomendationGenerator = new ArtistReccomendationGenerator(token, mOkHttpClient);
    }

    /**
     * initial spotify requests to get all appropiate songs
     */
    public void createWrapped() {

        final Request requestArtists = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/artists?time_range=" + timeFrame + "&limit=50")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        mCallArtists = mOkHttpClient.newCall(requestArtists);
        mCallArtists.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", " Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray artists = jsonObject.getJSONArray("items");
                    for (int i = 0; i < artists.length(); i++) {
                        Artist currArtist = new Artist(artists.getJSONObject(i));
                        topArtists.add(currArtist);
                    }
                    progressCounter++;

                } catch (JSONException e) {
                    Log.d("JSON","Failed to parse data: " + e);
                }

            }
        });

        final Request requestTracks = new Request.Builder()
                .url("https://api.spotify.com/v1/me/top/tracks?time_range=" + timeFrame + "&limit=3")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        mCallTracks = mOkHttpClient.newCall(requestTracks);
        mCallTracks.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray songs = jsonObject.getJSONArray("items");
                    for (int i = 0; i < songs.length(); i++) {
                        topSongs.add(new Song(songs.getJSONObject(i)));
                    }
                    nextSteps();
                    progressCounter++;
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                }
            }
        });


    }

    /**
     * next steps of generation where calling upon other
     * generators to modify data
     */
    private void nextSteps() {
        try {
            TimeUnit.SECONDS.sleep(1);
            generatedWrapped = new Wrapped(timeFrame, topSongs, topArtists);
            llmGenerator.generate(generatedWrapped, context);
           // songGenerator.generate(topSongs);
            for (int i = 0; i < 3; i++) {
                artistReccomendationGenerator.generate(topArtists.get(i));
            }
        } catch (Exception e) {
            Log.d("Generator","time failure");
        }
    }

    /**
     * getter for the Wrapped object created
     * @return generated Wrapped
     */
    public Wrapped getWrapped() {
        return generatedWrapped;
    }

    /**
     * getter for the progress in generation
     * @return double value from 0 to 1 representing how much work has been done
     */
    public double getProgress() {
        return ((double) progressCounter + llmGenerator.getProgress() + songGenerator.getProgress())/totalCalls;
    }

    /**
     * delay program by seconds
     * @param seconds seconds of time
     */
    private void delay(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
