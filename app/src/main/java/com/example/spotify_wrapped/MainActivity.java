package com.example.spotify_wrapped;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.spotify_wrapped.data_collection.Wrapped;
import com.example.spotify_wrapped.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private String userSpotifyToken;

    private ActivityMainBinding binding;
    private Wrapped displayWrapped;

    private UserDBAccess mainDBAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        Intent intent = getIntent();
        userSpotifyToken = intent.getStringExtra("token");

        mainDBAccess = new UserDBAccess(intent.getStringExtra("id"));
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
//        NavigationUI.setupWithNavController(binding.navView, navController);
        setupNavController(navController);
    }

    private void setupNavController(NavController navController) {
        binding.navView.setOnItemSelectedListener(menuItem -> {
            navController.navigate(menuItem.getItemId());
            return true;
        });
    }

    public void setDisplayWrapped(Wrapped displayWrapped) {
        this.displayWrapped = displayWrapped;
    }

    public Wrapped getDisplayWrapped() {
        return displayWrapped;
    }

    public UserDBAccess getMainDBAccess() {
        return mainDBAccess;
    }
}