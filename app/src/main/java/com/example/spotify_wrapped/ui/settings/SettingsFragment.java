package com.example.spotify_wrapped.ui.settings;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebStorage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.spotify_wrapped.LoginActivity;
import com.example.spotify_wrapped.MainActivity;
import com.example.spotify_wrapped.R;
import com.example.spotify_wrapped.UserDBAccess;
import com.example.spotify_wrapped.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private UserDBAccess dbInstance;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //get user ID and fetch info from database
        dbInstance = ((MainActivity) getActivity()).getMainDBAccess();

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Resources res = getResources();
        dbInstance.getUsername((String usrNme) -> {
            binding.settingsHeader.setText(res.getString(R.string.settings_header, usrNme));
        });


        binding.changeUserButton.setOnClickListener(view1 -> {
            if (binding.usernameInput.getText().toString().length() != 0) {
                try {
                    dbInstance.setUsername(binding.usernameInput.getText().toString());
                    dbInstance.getUsername((String s) -> {
                        binding.settingsHeader.setText(res.getString(R.string.settings_header, s));
                    });

                } catch (Exception e) {
                    Log.d("UserDBAccess", "User has been deleted");
                }

                binding.usernameInput.setText("");
            }
        });

        binding.deleteAccountButton.setOnClickListener(v -> {
            dbInstance.deleteAccount();
            navigateToForceLogin(view);
        });

        binding.logoutButton.setOnClickListener(v -> {
            navigateToForceLogin(view);
        });
    }

    private void navigateToForceLogin(View view) {
        Intent intent = new Intent(view.getContext(), LoginActivity.class);
        intent.putExtra("forceLogin", true);

        WebStorage.getInstance().deleteAllData();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush(); // TODO move this into login activity (/ w other login logic) to adhere to SRP

        view.getContext().startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}