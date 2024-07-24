package com.example.spotify_wrapped.data_collection;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.text.SimpleDateFormat;

/**
 * Wrapped class that contains all the values
 * needed for Wrapped functionality
 */
public class Wrapped implements Serializable {
    private String timeFrame;
    private List<Song> topSongs;
    private List<Artist> topArtists;
    private HashMap<String, Long> topGenres;
    private String location;
    private String hobbies;
    private String dress;
    private String socialGroup;
    private String time;

    /**
     * construcrtor for Wrapped object
     * @param timeFrame time frame of wrapped
     * @param topSongs list of songs
     * @param topArtists list of artists
     */
    public Wrapped(String timeFrame, List<Song> topSongs, List<Artist> topArtists) {
        this.timeFrame = timeFrame;
        this.topSongs = topSongs;
        this.topArtists = topArtists;
        topGenres = new HashMap<String, Long>();
        for (Artist artist : topArtists) {
            for (String genre : artist.getGenres()) {
                if (!topGenres.containsKey(genre)) {
                    topGenres.put(genre, 1L);
                } else {
                    topGenres.put(genre, topGenres.get(genre) + 1);
                }
            }
        }
        DateFormat df = new SimpleDateFormat();
        Date today = Calendar.getInstance()
                .getTime();
        time = df.format(today);

    }

    /**
     * getter for the time frame
     * @return time frame of wrapped
     */
    public String getTimeFrame() {
        return timeFrame;
    }

    /**
     * getter for string version of time frame
     * @return string time frame
     */
    public String getTimeFrameString() {
        if (timeFrame.equals("short_term")) {
            return "1 Month";
        } else if (timeFrame.equals("medium_term")) {
            return "6 Months";
        } else {
            return "All Time";
        }
    }

    /**
     * get Song object from list
     * @param ranking number item of the song list
     * @return Song object
     */
    public Song getSong(int ranking) {
        return topSongs.get(ranking);
    }

    /**
     * getter for all the song list
     * @return list of songs
     */
    public List<Song> getTopSongs() {
        return topSongs;
    }

    /**
     * getter for the artist
     * @param ranking number item of the artist list
     * @return Artist object
     */
    public Artist getArtist(int ranking) {
        return topArtists.get(ranking);
    }

    /**
     * getter for the list of artists
     * @return list of artists
     */
    public List<Artist> getTopArtists() {
        return topArtists;
    }

    /**
     * getter for the location
     * @return the string location
     */
    public String getLocation() {
        return location;
    }

    /**
     * setter for the location
     * @param location string new location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * getter for the hobbies
     * @return string of hobbies
     */
    public String getHobbies() {
        return hobbies;
    }

    /**
     * setter for hobbies
     * @param hobbies new string of hobbies
     */
    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    /**
     * getter for dress
     * @return string of dress
     */
    public String getDress() {
        return dress;
    }

    /**
     * setter for dress
     * @param dress new string of dress
     */
    public void setDress(String dress) {
        this.dress = dress;
    }

    /**
     * getter of social group
     * @return string of social group
     */
    public String getSocialGroup() {
        return socialGroup;
    }

    /**
     * setter for social group
     * @param socialGroup new string of social group
     */
    public void setSocialGroup(String socialGroup) {
        this.socialGroup = socialGroup;
    }

    /**
     * getter for top genres
     * @return hashmap of top genres and frequencies
     */
    public HashMap<String, Long> getTopGenres() {
        return topGenres;
    }

    /**
     * setter for top genres
     * @param tg new hashmap of top genres and frequencies
     */
    public void setTopGenres(HashMap<String, Long> tg) {
        this.topGenres = tg;
    }

    /**
     * getter for the average danceability of songs
     * @return average danceability of sogns
     */
    public double getAverageDanceability() {
        double sum = 0;
        for (Song song : topSongs) {
            sum += song.getDanceability();
        }
        return sum / topSongs.size();
    }
    /**
     * get average energy of top songs
     * @return average energy of wrapped
     */
    public double getAverageEnergy() {
        double sum = 0;
        for (Song song : topSongs) {
            sum += song.getEnergy();
        }
        return sum / topSongs.size();
    }
    /**
     * get average instrumentality of top songs
     * @return average instrumentality of wrapped
     */
    public double getAverageInstrumentality() {
        double sum = 0;
        for (Song song : topSongs) {
            sum += song.getInstrumentality();
        }
        return sum / topSongs.size();
    }

    /**
     * get average valence of top songs
     * @return average valence of wrapped
     */
    public double getAverageValence() {
        double sum = 0;
        for (Song song : topSongs) {
            sum += song.getInstrumentality();
        }
        return sum / topSongs.size();
    }

    /**
     * get list of reccomended artists
     * @return set of reccomended artists but in list form
     */
    public List<Artist> getReccomendedArtists() {
        Set<Artist> reccomendedArtists = new HashSet<Artist>();
        for (int i = 0; i < 3; i++) {
            reccomendedArtists.addAll(topArtists.get(i).getReccomendedArtists());
        }
        reccomendedArtists.removeAll(topArtists);

        List<Artist> arr = new ArrayList<Artist>();
        arr.addAll(reccomendedArtists);

        return arr;
    }

    /**
     * getter for time
     * @return String time
     */
    public String getTime() {
        return time;
    }

    /**
     * setter for time
     * @param time String time
     */
    public void setTime(String time) {
        this.time = time;
    }



}