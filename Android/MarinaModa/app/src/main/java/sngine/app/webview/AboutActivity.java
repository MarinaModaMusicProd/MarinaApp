package sngine.app.webview;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.about_title));
        }

        TextView appNameText = findViewById(R.id.app_name);
        TextView versionText = findViewById(R.id.version);
        TextView descriptionText = findViewById(R.id.description);
        TextView featuresText = findViewById(R.id.features);

        appNameText.setText(getString(R.string.app_name));

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionText.setText(getString(R.string.version_label) + " " + version);
        } catch (PackageManager.NameNotFoundException e) {
            versionText.setText(getString(R.string.version_label) + " N/A");
        }

        descriptionText.setText(getString(R.string.about_description));
        featuresText.setText(getString(R.string.about_features));
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

