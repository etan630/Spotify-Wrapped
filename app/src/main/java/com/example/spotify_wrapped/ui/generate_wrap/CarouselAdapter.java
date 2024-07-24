package com.example.spotify_wrapped.ui.generate_wrap;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.ui.generate_wrap.cards.ArtistRecommendationCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.BaseCardFragment;
import com.example.spotify_wrapped.ui.generate_wrap.cards.ClothesCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.CoverCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.EndWrapCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.GeneralWrapCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.GuessTopGameCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.HangoutLLMCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.HobbiesCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.LLMCard;
import com.example.spotify_wrapped.ui.generate_wrap.cards.LyricGameCard;

import java.util.Arrays;
import java.util.List;

public class CarouselAdapter extends FragmentStateAdapter {
    private static final List<Class<? extends BaseCardFragment>> CARDS = Arrays.asList(
            CoverCard.class,
            GuessTopGameCard.class,
            GeneralWrapCard.class,
            LLMCard.class,
            HangoutLLMCard.class,
            LyricGameCard.class,
            ArtistRecommendationCard.class,
            ClothesCard.class,
            HobbiesCard.class,
            EndWrapCard.class
    );
    protected List<Class<? extends BaseCardFragment>> getCards() {
        return CARDS;
    }

    private Wrapped displayWrapped;

    public CarouselAdapter(@NonNull FragmentManager fm, @NonNull Lifecycle lifecycle) {
        super(fm, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        try {
            Fragment fragment = getCards().get(position).newInstance();

            Bundle args = new Bundle();
            args.putSerializable("wrap", displayWrapped);

            fragment.setArguments(args);

            return fragment;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return getCards().size();
    }

    @Override
    public long getItemId(int position) {
        return position
                + (displayWrapped != null ? displayWrapped.hashCode() : 0); // force recreating fragments if displayWrapped has changed
    }

    public static class CarouselPageTransformer implements ViewPager2.PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            float width = page.getWidth();
            float alpha = 1 - Math.abs(position);

            // create glow effect when fragment is in the center of the screen
            if (alpha > 0.5f) {
                page.setTranslationX(-width * position);
                page.setScaleX(alpha * 0.8f + 0.2f);
                page.setScaleY(alpha * 0.8f + 0.2f);
                page.setAlpha(alpha);
            }

            //create blur effect when fragment si on edge of screen
            else {
                page.setAlpha(alpha / 2);
            }
        }
    }

    public void setDisplayWrapped(Wrapped displayWrapped) {
        this.displayWrapped = displayWrapped;
    }
}
