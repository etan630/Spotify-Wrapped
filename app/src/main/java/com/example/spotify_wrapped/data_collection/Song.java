package com.example.spotify_wrapped.data_collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Song class that represents a Song object
 */
public class Song implements Serializable {
    /**
     * setter for the name of the song
     * @param name name of song
     */
  public void setName(String name) {
        this.name = name;
    }
    private String name;

    /**
     * setter for the id
     * @param id new id
     */
    public void setId(String id) {
        this.id = id;
    }

    private String id;

    /**
     * setter for the image link
     * @param imageLink new image link
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    private String imageLink;

    /**
     * setter for the artists
     * @param artists list of artists of the song
     */
    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    private List<Artist> artists;
    private String genre;

    /**
     * setter for the genre
     * @param genre String new genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    private double danceability;
    private double energy;
    private double instrumentality;
    private double valence;
    private String lyric;

    /**
     * Song Constructor
     * @param reference JSON reference to scrape the content from
     */
    public Song(JSONObject reference) {
        try {
            name = reference.getString("name");
            imageLink = ((JSONObject) reference.getJSONObject("album").getJSONArray("images").get(0)).getString("url");
            artists  = new ArrayList<Artist>();
            JSONArray artistsJSON = reference.getJSONArray("artists");
            for (int i = 0; i < artistsJSON.length(); i++) {
                Artist currArtist = new Artist(artistsJSON.getJSONObject(i));
                artists.add(currArtist);
            }
            id = reference.getString("id");
        } catch (JSONException e) {

        }
    }

    /**
     * blank constructor
     */
    public Song() {

    }

    /**
     * getter for the name
     * @return Sring name
     */
    public String getName() {
        return name;
    }

    /**
     * getter the id
     * @return String id
     */
    public String getId() {
        return id;
    }

    /**
     * getter for the list of artists of the song
     * @return the list of artists
     */
    public List<Artist> getArtists() {
        return artists;
    }

    /**
     * getter for the genre of the song
     * @return string of genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * getter for the danceability value
     * @return double danceability
     */
    public double getDanceability() {
        return danceability;
    }

    /**
     * setter for the danceability value
     * @param danceability double new danceability
     */
    public void setDanceability(double danceability) {
        this.danceability = danceability;
    }

    /**
     * getter for the energy value
     * @return double value
     */
    public double getEnergy() {
        return energy;
    }

    /**
     * setter for the energy
     * @param energy new energy value
     */
    public void setEnergy(double energy) {
        this.energy = energy;
    }

    /**
     * getter for the instrumentality value
     * @return double instrumentality
     */
    public double getInstrumentality() {
        return instrumentality;
    }

    /**
     * setter for the instrumentality
     * @param instrumentality double instrumentality
     */
    public void setInstrumentality(double instrumentality) {
        this.instrumentality = instrumentality;
    }

    /**
     * getter for the valence
     * @return double valence
     */
    public double getValence() {
        return valence;
    }

    /**
     * setter for the valence
     * @param valence new valence value
     */
    public void setValence(double valence) {
        this.valence = valence;
    }

    /**
     * getter for the image link
     * @return image link
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * setter for the lyric
     * @param lyric lyric of the song
     */
    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    /**
     * getter for the lyric
     * @return lyric of the song
     */
    public String getLyric() {
        return lyric;
    }

    /**
     * overrides to String
     * @return name of song
     */
    @Override
    public String toString() {
        return name;
    }
}