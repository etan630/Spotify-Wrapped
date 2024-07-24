package com.example.spotify_wrapped.ui.past_wraps;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.spotify_wrapped.R;
import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.databinding.FragmentDisplayPastBinding;
import com.example.spotify_wrapped.ui.generate_wrap.CarouselAdapter;


/**
 * Fragment to display one past wrapped.
 */
public class DisplayPastFragment extends Fragment {
    private FragmentDisplayPastBinding binding;
    private Wrapped wrapped;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDisplayPastBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        wrapped = getArguments().getSerializable("wrapped", Wrapped.class);

        setupCarousel();
        setupBackButton();

        binding.dateTextView.setText(wrapped.getTime().split(" ")[0]);
        binding.timeframePast.setText(wrapped.getTimeFrameString());
        return root;
    }

    private void setupCarousel() {
        ViewPager2 viewPager = binding.displayPastWrapCarousel;
        viewPager.setSaveEnabled(false);

        CarouselPastAdapter adapter = new CarouselPastAdapter(getChildFragmentManager(), getLifecycle());

        adapter.setDisplayWrapped(wrapped);

        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(new CarouselPastAdapter.CarouselPageTransformer());

        // default view when first open the app
        viewPager.setCurrentItem(0);
        // disables user input to allow cycling through the fragments
//        viewPager.setUserInputEnabled(false);
    }

    private void setupBackButton() {
        Button back = binding.btnBack;
        back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
    }
}