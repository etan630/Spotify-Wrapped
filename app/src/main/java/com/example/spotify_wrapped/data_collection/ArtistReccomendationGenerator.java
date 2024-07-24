package com.example.spotify_wrapped.data_collection;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ArtistReccomendationGenerator class takes the Artist object and
 * does a Spotify API call to get a list of reccomended Artists
 * based on the artist
 */
public class ArtistReccomendationGenerator {
    private String token;
    private OkHttpClient mOkHttpClient;

    /**
     * constructor for the generator
     * @param token spotify token to utilize
     * @param mOkHttpClient Http client that will send the API request
     */
    public ArtistReccomendationGenerator(String token, OkHttpClient mOkHttpClient) {
        this.token = token;
        this.mOkHttpClient = mOkHttpClient;
    }

    /**
     * makes the api call to get reccomended Artist and fills
     * the Artist object with the reccomended Artists
     * @param artist artist we want reccomended Artists for
     */
    public void generate(Artist artist) {
        String id = artist.getId();
        final Request requestTracks = new Request.Builder()
                .url("https://api.spotify.com/v1/artists/" + id + "/related-artists")
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
                    JSONArray artists = jsonObject.getJSONArray("artists");
                    for (int i = 0; i < artists.length(); i++) {
                        Artist currArtist = new Artist(artists.getJSONObject(i));
                        artist.addArtist(currArtist);
                    }
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                }
            }
        });
    }

}
