package com.witold.mapapp.view.map_settings_activity;

import android.app.Activity;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.MenuItem;

import com.witold.mapapp.R;
import com.witold.mapapp.view.main_activity.MainActivity;

import java.util.List;

public class MapSettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Ustawienia mapy");
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new MapPreferenceFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        setResult(MainActivity.SUCCESS_RESULT_CODE);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MapPreferenceFragment extends PreferenceFragmentCompat
    {
        @Override
        public void onCreatePreferences(Bundle bundle, String s) {
            // Load the Preferences from the XML file
            addPreferencesFromResource(R.xml.map_preferences);
        }
    }
}
