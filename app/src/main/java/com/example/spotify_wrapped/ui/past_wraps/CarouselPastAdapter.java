package com.example.spotify_wrapped.ui.past_wraps;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.ui.generate_wrap.CarouselAdapter;
import com.example.spotify_wrapped.ui.generate_wrap.cards.ArtistRecommendationCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.BaseCardFragment;
import com.example.spotify_wrapped.ui.generate_wrap.cards.ClothesCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.GeneralWrapCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.HangoutLLMCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.HobbiesCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.LLMCard;

import java.util.Arrays;
import java.util.List;
public class CarouselPastAdapter extends CarouselAdapter {
    private static final List<Class<? extends BaseCardFragment>> CARDS = Arrays.asList(
            GeneralWrapCard.class,
            LLMCard.class,
            HangoutLLMCard.class,
            ArtistRecommendationCard.class,
            ClothesCard.class,
            HobbiesCard.class
    );
    @Override
    protected List<Class<? extends BaseCardFragment>> getCards() {
        return CARDS;
    }

    public CarouselPastAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle) {
        super(fm, lifecycle);
    }
}
