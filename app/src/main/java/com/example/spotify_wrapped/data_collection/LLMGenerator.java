package com.example.spotify_wrapped.data_collection;

import android.content.Context;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * LLM generator class that will generate a bunch of LLM messages
 * based on the Spotify API requests utilizing Gemini API
 */
public class LLMGenerator {
    private static final String geminiKey = "AIzaSyC4UPjh2Ar9CJr6ybJX2kTomFQWl6e_jAg";
    private int progress;
    public LLMGenerator(){
        progress = 0;
    }

    /**
     * Generator that gets all the content from the LLM content
     * @param wrapped Wrapped object to generate LLM content
     * @param context context of the app
     */
    public void generate(Wrapped wrapped, Context context) {
        List<SafetySetting> safetySettings = Arrays.asList(
                new SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.NONE)
                , new SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.NONE)
                , new SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.NONE)
                , new SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.NONE)
//                , new SafetySetting(HarmCategory.UNKNOWN, BlockThreshold.NONE)
        ); // lol

        GenerativeModel gm = new GenerativeModel("gemini-pro", geminiKey, null, safetySettings);

        GenerativeModelFutures model = GenerativeModelFutures.from(gm);
        String artists = wrapped.getArtist(0).getName() + ", " +
                wrapped.getArtist(1).getName() + ", and " +
                wrapped.getArtist(2).getName();
        Content prompt = new Content.Builder()
                .addText("Give me a two word response in the format of city, country of where a typical listener of " + artists + " would live")
                .build();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(prompt);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                wrapped.setLocation(resultText);
                progress++;
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, context.getMainExecutor());

        prompt = new Content.Builder()
                .addText("Based on my top artists of " + artists + ", what three hobbies do you think I like? (respond in five words" +
                        "and can be unrelated to music) All elements should come from the following list and be in all lowercase:" + "playing instruments\n" +
                        "art\n" +
                        "cooking\n" +
                        "singing\n" +
                        "baking\n" +
                        "photography\n" +
                        "video games\n" +
                        "crocheting\n" +
                        "reading\n" +
                        "sports")
                .build();

        response = model.generateContent(prompt);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                wrapped.setHobbies(resultText);
                progress++;
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, context.getMainExecutor());

        prompt = new Content.Builder()
                .addText("Based on my top artists of " + artists + ", what type of clothing do you think I like? (response in three words)")
                .build();

        response = model.generateContent(prompt);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                wrapped.setDress(resultText);
                progress++;
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, context.getMainExecutor());

        HashMap<String, Long> genres = wrapped.getTopGenres();
        Set<String> genreNames = genres.keySet();
        String topGenreName = "";
        long topGenreFrequency = -1;
        for (String genre: genreNames) {
            if (genres.get(genre) > topGenreFrequency) {
                topGenreName = genre;
                topGenreFrequency = genres.get(genre);
            }
        }
        prompt = new Content.Builder()
                .addText("Based on my top genre of " + topGenreName + ", what type of people would I like to hang out with? (response in three words" +
                        " seperated by commas)")
                .build();

        response = model.generateContent(prompt);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                wrapped.setSocialGroup(resultText);
                progress++;
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, context.getMainExecutor());

        prompt = new Content.Builder()
                .addText(
                        "Give me most popular short lyric from " + wrapped.getSong(0) + " by " +
                                wrapped.getSong(0).getArtists().get(0) +
                                "without any formatting, but capitalize the first letter of each word " +
                                "dont include artists and names")
                .build();

        response = model.generateContent(prompt);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                wrapped.getSong(0).setLyric(resultText);
                progress++;
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        }, context.getMainExecutor());
    }

    public int getProgress() {
        return progress;
    }
}
