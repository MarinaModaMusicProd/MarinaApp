package sngine.app.webview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_FIRST_LAUNCH = "first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        // Check if this is the first launch
        boolean isFirstLaunch = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true);
        
        if (!isFirstLaunch) {
            // Not first launch, go directly to MainActivity
            startMainActivity();
            return;
        }
        
        setContentView(R.layout.activity_welcome);
        
        Button getStartedButton = findViewById(R.id.btn_get_started);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mark that we've shown the welcome screen
                sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply();
                startMainActivity();
            }
        });
    }
    
    private void startMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

