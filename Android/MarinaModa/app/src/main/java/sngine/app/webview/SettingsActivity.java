package sngine.app.webview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private Switch darkModeSwitch;
    private Switch notificationsSwitch;
    private Switch autoRefreshSwitch;
    private Switch locationSwitch;
    private Switch biometricSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.settings_title));
        }

        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        darkModeSwitch = findViewById(R.id.switch_dark_mode);
        notificationsSwitch = findViewById(R.id.switch_notifications);
        autoRefreshSwitch = findViewById(R.id.switch_auto_refresh);
        locationSwitch = findViewById(R.id.switch_location);
        biometricSwitch = findViewById(R.id.switch_biometric);

        // Load saved preferences
        darkModeSwitch.setChecked(sharedPreferences.getBoolean("dark_mode", false));
        notificationsSwitch.setChecked(sharedPreferences.getBoolean("notifications", true));
        autoRefreshSwitch.setChecked(sharedPreferences.getBoolean("auto_refresh", true));
        locationSwitch.setChecked(sharedPreferences.getBoolean("location", true));
        biometricSwitch.setChecked(sharedPreferences.getBoolean("biometric", true));

        // Set listeners
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("dark_mode", isChecked).apply();
                if (MainActivity.class.isInstance(MainActivity.class)) {
                    // Notify MainActivity to apply dark mode
                }
            }
        });

        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("notifications", isChecked).apply();
            }
        });

        autoRefreshSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("auto_refresh", isChecked).apply();
            }
        });

        locationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("location", isChecked).apply();
            }
        });

        biometricSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.edit().putBoolean("biometric", isChecked).apply();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

