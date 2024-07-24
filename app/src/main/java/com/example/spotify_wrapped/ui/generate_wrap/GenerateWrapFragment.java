package com.example.spotify_wrapped.ui.generate_wrap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.spotify_wrapped.MainActivity;
import com.example.spotify_wrapped.UserDBAccess;
import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.data_collection.WrappedGenerator;
import com.example.spotify_wrapped.databinding.FragmentGenerateWrapBinding;
import com.google.firebase.firestore.auth.User;

import java.util.concurrent.Executors;

import javax.annotation.Nullable;

public class GenerateWrapFragment extends Fragment {
    FragmentGenerateWrapBinding binding;
    private ViewPager2 viewPager;
    private String term;

    private WrappedGenerator generator;
    private Wrapped generatedWrapped;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenerateWrapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewPager = binding.displayWrapCarousel;
        viewPager.setSaveEnabled(false);

        Button generateButton = binding.generateButton;
        Spinner timeOptions = binding.timeOptions;
        timeOptions.setOnItemSelectedListener(new SpinnerFunctionality());

        Intent intent = requireActivity().getIntent();
        String token = intent.getStringExtra("token");
        String userID = intent.getStringExtra("id");
        System.out.println("inside generatewrapfragment, userID is:" + userID);

        // set up click listener for the generate button
        generateButton.setOnClickListener(view -> generateAndDisplayWrapped(token));

        // for carousel display/page transitions
        setupViewPager();
        return root;
    }

    private void generateAndDisplayWrapped(String token) {
        Executors.newSingleThreadExecutor().execute(() -> { // on a new thread
            Handler handler = new Handler(Looper.getMainLooper()); // get main (UI) thread

            handler.post(() -> {
                binding.wrapLoading.setVisibility(View.VISIBLE);
            });

            generator = new WrappedGenerator(term, token, getContext());

            while (generator.getProgress() < 1) {
                try {
                    handler.post(() -> {
                        binding.wrappedProgress.setProgress((int) (generator.getProgress() * 100));
                    });
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e); // TODO
                }
            }

            generatedWrapped = generator.getWrapped();
            //Store wrapped object here
            UserDBAccess tmp = ((MainActivity) getActivity()).getMainDBAccess();
            tmp.addWrapped(generatedWrapped);

            handler.post(() -> {
                ((CarouselAdapter) viewPager.getAdapter()).setDisplayWrapped(generatedWrapped);
                viewPager.setCurrentItem(1);
                viewPager.setUserInputEnabled(true);

                binding.wrapLoading.setVisibility(View.INVISIBLE);
            });
        });
    }

    private void setupViewPager() {
        viewPager.setAdapter(new CarouselAdapter(getChildFragmentManager(), getLifecycle()));
        viewPager.setPageTransformer(new CarouselAdapter.CarouselPageTransformer());

        // default view when first open the app
        viewPager.setCurrentItem(0);
        // disables user input to allow cycling through the fragments
        viewPager.setUserInputEnabled(false);
    }

    private class SpinnerFunctionality implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:
                    term = "short_term";
                    break;
                case 1:
                    term = "medium_term";
                    break;
                default:
                    term = "long_term";
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            term = "long_term";
        }

    }
}