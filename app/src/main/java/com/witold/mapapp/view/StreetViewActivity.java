package com.witold.mapapp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.witold.mapapp.R;
import com.witold.mapapp.model.Place;

public class StreetViewActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback {
    private StreetViewPanoramaFragment streetViewPanoramaFragment;
    private Place placeToShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Widok ulicy");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_street_view);
        this.placeToShow = (Place) getIntent().getSerializableExtra("place");
        streetViewPanoramaFragment =
        (StreetViewPanoramaFragment) getFragmentManager()
                .findFragmentById(R.id.streetViewPanoramaFragment);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
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

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(new LatLng(placeToShow.getLatitude(), placeToShow.getLongitude()));
        streetViewPanorama.setUserNavigationEnabled(true);

    }
}
