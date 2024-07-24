package com.example.spotify_wrapped.data_collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Artist Class to represent an Artist from Spotify
 */
public class Artist implements Serializable {
    /**
     * Setter for name
     * @param name new Name
     */
    public void setName(String name) {
        this.name = name;
    }
    private String name;

    /**
     * Setter for genres
     * @param genres new List of String Genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    private List<String> genres;

    /**
     * Setter for image link
     * @param imageLink String new Image Link
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     * Setter for String id
     * @param id String new id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Setter for List of Artist Recommended Artists
     * @param reccomendedArtists new List of Artist Recommended Artists
     */
    public void setReccomendedArtists(List<Artist> reccomendedArtists) {
        this.reccomendedArtists = reccomendedArtists;
    }

    private String imageLink;
    private String id;
    private List<Artist> reccomendedArtists;

    /**
     * Constructor for Artist object that utilizes JSON to extract each element neccessary
     * @param reference JSON reference that will be scraped for all the neccesary data
     */
    public Artist(JSONObject reference) {
        try {
            name = reference.getString("name");
            genres = new ArrayList<>();
            JSONArray jsonGenres = reference.getJSONArray("genres");
            for (int i = 0; i < jsonGenres.length(); i++) {
                genres.add(jsonGenres.getString(i));
            }
            imageLink = ((JSONObject) reference.getJSONArray("images").get(0)).getString("url");
            id = reference.getString("id");
            reccomendedArtists = new ArrayList<Artist>();
        } catch (JSONException e) {

        }
    }

    /**
     * Blank Constructor
     */
    public Artist() {

    }

    /**
     * getter for name of Artist object
     * @return name of Artist
     */
    public String getName() {
        return name;
    }

    /**
     * getter for list of genres
     * @return string list of genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * getter for the image link
     * @return image link of artist
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * getter for the id of the artist
     * @return id of the artist
     */
    public String getId() {
        return id;
    }

    /**
     * adder for Artist to reccomended List
     * @param artist new Artist to add
     */
    public void addArtist(Artist artist) {
        reccomendedArtists.add(artist);
    }

    /**
     * getter for the reccomended Artist List
     * @return List of Artist objects
     */
    public List<Artist> getReccomendedArtists() {
        return reccomendedArtists;
    }

    /**
     * overriding of toString
     * @return name of the Artist
     */
    @Override
    public String toString() {
        return name;
    }
}