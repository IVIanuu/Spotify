package com.ivianuu.spotify.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ivianuu.spotify.CurrentUser;
import com.ivianuu.spotify.Spotify;
import com.ivianuu.spotify.SpotifyConfig;
import com.ivianuu.spotify.api.scopes.ScopesBuilder;
import com.ivianuu.spotify.auth.AuthActivity;

public class MainActivity extends AppCompatActivity {

    private static final int LOGIN_REQUEST_CODE = 1832;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spotify.getInstance().getCurrentUser().logout();
                recreate();
            }
        });

        findViewById(R.id.show_user_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Spotify.getInstance().getCurrentUser().isLoggedIn()) {
                    Toast.makeText(MainActivity.this, Spotify.getInstance().getCurrentUser().getUserName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // NORMALY YOU WOULD DO THIS IN YOUR APPLICATION CLASS

        // Creating scopes
        String[] scopes = new ScopesBuilder()
                .withAllScopes()
                .build();

        // Configure
        SpotifyConfig config = new SpotifyConfig.Builder()
                .withContext(this)
                .withClientId("154dba9755364b4e904e5650e9a1134a")
                .withRedirectUri("spotifysample://callback")
                .withScopes(scopes)
                .build();

        // Initialize
        Spotify.init(config);

        // Login if necessary
        if (!Spotify.getInstance().getCurrentUser().isLoggedIn()) {
            Log.d("mytag", "is not logged in");
            Spotify.getInstance().getCurrentUser().login(this, LOGIN_REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LOGIN_REQUEST_CODE) {
            switch (resultCode) {
                case RESULT_OK:
                    // recreate to make changes apply
                    recreate();
                    Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
                    break;
                case RESULT_CANCELED:
                    // handle cancelled
                    Toast.makeText(this, "Login canceled", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    // handle login failed
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
