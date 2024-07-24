package com.example.spotify_wrapped.ui.past_wraps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spotify_wrapped.MainActivity;
import com.example.spotify_wrapped.R;
import com.example.spotify_wrapped.UserDBAccess;
import com.example.spotify_wrapped.data_collection.Artist;
import com.example.spotify_wrapped.data_collection.Song;
import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.databinding.FragmentPastWrapsBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Placeholder fragment for navigation
 */
public class PastWrapsFragment extends Fragment {

    private FragmentPastWrapsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPastWrapsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView list = binding.recyclerView;
        TextView history_title = binding.historyTitle;

        UserDBAccess dbAccess = ((MainActivity) getActivity()).getMainDBAccess();
        dbAccess.getPastWraps((List<HashMap<String, Object>> pastWraps) -> {
            //System.out.println(pastWraps.get(0));
            List<Wrapped> pastWrapsList = new ArrayList<>();
            for (HashMap<String, Object> map : pastWraps) {
                List<Song> songList = new ArrayList<>();
                ArrayList<HashMap<String, Object>> dbTopSongs = (ArrayList<HashMap<String, Object>>) map.get("topSongs");
                for (HashMap<String, Object> songMap : dbTopSongs) {
                    Song tmp = new Song();
                    tmp.setId((String) songMap.get("id"));
                    tmp.setName((String) songMap.get("name"));
                    tmp.setGenre((String) songMap.get("genre"));
                    tmp.setImageLink((String) songMap.get("imageLink"));
                    tmp.setDanceability((Double) songMap.get("danceability"));
                    tmp.setEnergy((Double) songMap.get("energy"));
                    tmp.setInstrumentality((Double) songMap.get("instrumentality"));
                    tmp.setLyric((String) songMap.get("lyric"));
                    tmp.setValence((Double) songMap.get("valence"));

                    ArrayList<HashMap<String, Object>> aSongArtist = (ArrayList<HashMap<String, Object>>) songMap.get("artists");
                    Artist theSongArtist = new Artist();
                    if (aSongArtist != null) {
                        if (aSongArtist.get(0) != null) {
                            theSongArtist.setName((String) aSongArtist.get(0).get("name"));
                        }
                    }
                    tmp.setArtists(Collections.singletonList(theSongArtist));

                    songList.add(tmp);
                }
                List<Artist> artistList = new ArrayList<>();
                ArrayList<HashMap<String, Object>> dbArtistList = (ArrayList<HashMap<String, Object>>) map.get("topArtists");
                for (HashMap<String, Object> artistMap : dbArtistList) {
                    Artist tmp = new Artist();
                    tmp.setId((String) artistMap.get("id"));
                    tmp.setName((String) artistMap.get("name"));
                    tmp.setGenres((List<String>) artistMap.get("genres"));
                    tmp.setImageLink((String) artistMap.get("imageLink"));

                    List<Artist> recommendedArtistArr = new ArrayList<>();
                    ArrayList<HashMap<String, Object>> recommendedArtistMap = (ArrayList<HashMap<String, Object>>) map.get("reccomendedArtists");
                    for (HashMap<String, Object> asdf : recommendedArtistMap) {
                        Artist p = new Artist();
                        p.setId((String) asdf.get("id"));
                        p.setName((String) asdf.get("name"));
                        p.setGenres((List<String>) asdf.get("genres"));
                        p.setImageLink((String) asdf.get("imageLink"));
                        recommendedArtistArr.add(p);
                    }
                    tmp.setReccomendedArtists(recommendedArtistArr);

                    artistList.add(tmp);
                }

                Wrapped newObj = new Wrapped((String) map.get("timeFrame"), songList, artistList);
                newObj.setDress((String) map.get("dress"));
                newObj.setHobbies((String) map.get("hobbies"));
                newObj.setLocation((String) map.get("location"));
                //newObj.setTime((String) map.get("time"));
                SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                //newObj.setTime(sfd.format(new Date((String) map.get("time"))));
                newObj.setSocialGroup((String) map.get("socialGroup"));
                newObj.setTopGenres((HashMap<String, Long>) map.get("topGenres"));
                pastWrapsList.add(newObj);

            }

            if (pastWrapsList.isEmpty()) {
                history_title.setText("Create Your First Wrap!");
            }

            Collections.reverse(pastWrapsList); // LIFO
            list.setAdapter(new WrappedListAdapter(pastWrapsList, Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main)));

            binding.fetchpastwrappedProgress.setVisibility(View.INVISIBLE);
        });


        return root;
    }

//    public void onCardClick(Wrapped wrapped) {
//        // pass data to new fragment
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("wrapped", wrapped);
//
//        DisplayPastFragment displayPastFragment = new DisplayPastFragment();
//        displayPastFragment.setArguments(bundle);
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}