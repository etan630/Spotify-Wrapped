package com.example.spotify_wrapped;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "954e43168b034224bc8e11f9fbb43727";
    private static final String REDIRECT_URI = "SpotifyWrapped://auth";
    private static final int REQUEST_CODE = 1337;
    private static final String SCOPES = "user-top-read";
    private final OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_login);

        Button button = findViewById(R.id.signInButton);
        button.setOnClickListener(view -> {
            findViewById(R.id.login_loading).setVisibility(View.VISIBLE);
            authenticateSpotify();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if the received result is from Spotify authentication
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, data);
            if (response.getType() == AuthorizationResponse.Type.TOKEN) {
                // Handle successful authentication
                String token = response.getAccessToken();
                getUserProfile(token);
            } else if (response.getType() == AuthorizationResponse.Type.ERROR) {
                // Handle authentication error
                //TODO what to do in a situation the code failed
            }
        }
    }

    private void authenticateSpotify() {
        AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{SCOPES});
        AuthorizationRequest request = builder.build();

        if (getIntent().getBooleanExtra("forceLogin", false)) {
            AuthorizationClient.clearCookies(this);
        }
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    public void getUserProfile(String token) {

        final Request userRequest = new Request.Builder()
                .url("https://api.spotify.com/v1/me")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        Call mCallUserProfile = mOkHttpClient.newCall(userRequest);
        mCallUserProfile.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", " Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    String username = jsonObject.getString("display_name");
                    String userID = jsonObject.getString("id");
                    moveOnAsync(username, userID, token);
                } catch (JSONException e) {
                    Log.d("JSON", "Failed to parse data: " + e);
                }
            }
        });
    }

    private void cancelCallUserProfile(Call mCallUserProfile) {
        if (mCallUserProfile != null) {
            mCallUserProfile.cancel();
        }
    }

    private void moveOnAsync(String username, String userID, String token) {
        runOnUiThread(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("token", token);
            intent.putExtra("id", userID);
            UserDBAccess dbInstance = new UserDBAccess(userID);
            //System.out.println(dbInstance.getUsername());
            dbInstance.getUsername((String s) -> {
                if (s.equals("")) {
                    setContentView(R.layout.login_username_page);
                    Button confirmButton = findViewById(R.id.createUsernameButton);
                    confirmButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText mEdit = (EditText) findViewById(R.id.username);
                            try {
                                dbInstance.setUsername(mEdit.getText().toString());
                            } catch (Exception e) {
                            }

                            startActivity(intent);
                            finish();
                        }
                    });

                } else {
                    startActivity(intent);
                    finish();
                }
            });

        });
    }
}