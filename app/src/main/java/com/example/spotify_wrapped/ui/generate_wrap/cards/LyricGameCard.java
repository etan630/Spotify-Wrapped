package com.example.spotify_wrapped.ui.generate_wrap.cards;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.spotify_wrapped.data_collection.ImageGenerator;
import com.example.spotify_wrapped.databinding.CardLyricGameBinding;
import javax.annotation.Nullable;


public class LyricGameCard extends BaseCardFragment {
    //TODO move this string array  to a seperate data file later
    private String[] lyrics = {"Is this the real life? Is this just fantasy?", "I'm a Barbie girl, in a Barbie world", "I gotta feeling that tonight's gonna be a good night", "Just a small town girl, livin' in a lonely world", "Bohemian Rhapsody", "I will survive", "Imagine there's no heaven", "Like a rolling stone", "Hey Jude", "Sweet Child o' Mine", "Billie Jean", "Hotel California", "Yesterday", "Your song", "Dancing Queen", "Good Vibrations", "Don't Stop Believin'", "Can't Stop The Feeling!", "I Want To Break Free", "Respect", "What a Wonderful World", "Born to Run", "Hallelujah", "Smells Like Teen Spirit", "I Will Always Love You", "Losing My Religion", "Wonderwall", "Hey Ya!", "Mr. Brightside", "Can't Get You Out Of My Head", "Clocks", "Poker Face", "Single Ladies (Put a Ring on It)", "Rolling in the Deep", "Somebody That I Used To Know", "Let It Go", "Happy", "Uptown Funk", "Thinking Out Loud", "Hello", "Despacito", "Shape of You", "See You Again", "Someone You Loved", "Bad Guy", "Dance Monkey", "Blinding Lights", "Watermelon Sugar", "Dynamite", "Drivers License", "Montero (Call Me By Your Name)", "Stay", "Levitating", "Peaches", "Good 4 U", "Kiss Me More", "Industry Baby", "Save Your Tears", "Leave The Door Open", "Butter", "Permission to Dance", "My Universe", "Bad Habits", "Shivers", "Easy On Me", "All Too Well (10 Minute Version)", "Heat Waves", "Cold Heart (Pnau Remix)", "Woman", "Enemy", "Ghost", "abcdefu", "One Right Now", "We Don't Talk About Bruno", "Super Gremlin", "Do It To It", "Pushin P", "Thats What I Want", "Big Energy", "Ah Ha", "Wait For U", "First Class", "About Damn Time", "As It Was", "Running Up That Hill (A Deal With God)", "Break My Soul", "I Like You (A Happier Song)", "Late Night Talking", "Jimmy Cooks", "Me Porto Bonito", "Tití Me Preguntó", "Ojitos Lindos", "Moscow Mule", "Vegas", "The Kind of Love We Make", "Sunroof", "Something In The Orange", "Wasted On You", "You Proof", "She Had Me At Heads Carolina", "Fall In Love", "Like I Love Country Music", "5 Foot 9", "Heartfirst", "New Truck", "No Body", "Son Of A Sinner", "Circles Around This Town", "If I Was A Cowboy", "Never Wanted To Be That Girl", "Never Say Never", "half of my hometown", "Doin' This", "Buy Dirt", "Things A Man Oughta Know", "Fancy Like", "To Be Loved By You", "Sand In My Boots", "You Should Probably Leave", "Take My Name", "Forever After All", "7 Summers", "Best Friends", "I Was On a Boat That Day", "Hell Of A View", "Starting Over", "Just the Way", "Lady", "Beer Never Broke My Heart", "One Thing Right", "10,000 Hours", "God's Country", "Knockin' Boots", "Whiskey Glasses", "Chasin' You", "What Ifs", "She Got the Best of Me", "Beautiful Crazy", "Speechless", "Die From A Broken Heart", "Prayed For You", "Get Along", "Good Girl", "Homesick", "This Is It", "In Case You Didn't Know", "Yours", "Heaven", "Greatest Love Story", "Hurricane", "Body Like A Back Road", "Craving You", "I Could Use A Love Song", "Small Town Boy", "It Ain't My Fault", "When It Rains It Pours", "Unforgettable", "More Girls Like You", "Marry Me"};
    private CardLyricGameBinding binding;
  
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CardLyricGameBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // initializing the components of the card
        TextView artistNameText = binding.topSongArtistLyric;
        TextView songNameText = binding.topSongTileLyric;
        ImageView albumCover = binding.topSongAlbumCover;

        RadioButton choice1 = binding.choice1;
        RadioButton choice2 = binding.choice2;
        RadioButton choice3 = binding.choice3;
        RadioButton choice4 = binding.choice4;

        TextView correctAnswerLyrics = binding.correctAnswerLyrics;
        Button submitGuessButtonLyric = binding.submitGuessButtonLyric;

        // getting song name, artist name, and image link
        String songName = getWrapToDisplay().getSong(0).getName();
        String artistName = getWrapToDisplay().getSong(0).getArtists().get(0).getName();
        String link = getWrapToDisplay().getSong(0).getImageLink();

        // generating random lyrics for the choices
        int randomLyric = (int) (Math.random() * lyrics.length/4);
        choice1.setText(lyrics[randomLyric]);
        choice2.setText(lyrics[randomLyric + lyrics.length/4]);
        choice3.setText(lyrics[randomLyric + lyrics.length/2]);
        choice4.setText(lyrics[randomLyric + 3 * lyrics.length/4]);

        // setting the artist name and song name for the card
        artistNameText.setText(artistName);
        songNameText.setText(songName);

        // generating album cover
        //TODO fix so not using hard dimensions
        ImageGenerator.generate(link, albumCover, 100, 100);

        // selecting a random index for the correct lyric
        int index = (int)(Math.random() * 4);
        String lyric = getWrapToDisplay().getSong(0).getLyric();

        if (index == 0) {
            choice1.setText(lyric);
        }

        // setting correct lyric for the selected index
        switch (index){
            case 0:
                choice1.setText(lyric);
                break;
            case 1:
                choice2.setText(lyric);
                break;
            case 2:
                choice3.setText(lyric);
                break;
            case 3:
                choice4.setText(lyric);
                break;
        }
        int color = Color.parseColor("#006600");


        // setting click listener for the submit guess button
        submitGuessButtonLyric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton selectedButton = binding.lyricChoices.findViewById(binding.lyricChoices.getCheckedRadioButtonId());
                if (selectedButton.getText().toString().equals(lyric)) {
                    selectedButton.setTextColor(color);
                    correctAnswerLyrics.setText("Correct!");
                    correctAnswerLyrics.setTextColor(color);
                } else {
                    selectedButton.setTextColor(Color.RED);
                    correctAnswerLyrics.setText("Wrong!");
                    correctAnswerLyrics.setTextColor(Color.RED);
                }
            }
        });

        return root;
    }


}