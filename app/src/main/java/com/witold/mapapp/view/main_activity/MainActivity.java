package com.witold.mapapp.view.main_activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.TravelMode;
import com.witold.mapapp.R;
import com.witold.mapapp.SharedPreferencesController;
import com.witold.mapapp.model.Place;
import com.witold.mapapp.model.Trip;
import com.witold.mapapp.view.StreetViewActivity;
import com.witold.mapapp.view.map_settings_activity.MapSettingsActivity;
import com.witold.mapapp.view.trip_list_activity.TripListActivity;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, PlaceInfoViewHolder {
    public static final int TRIP_LIST_ACTIVITY_REQUEST = 101;
    public static final int MAP_SETTINGS_ACTIVITY_REQUEST = 201;
    public static final int SUCCESS_RESULT_CODE = 1;
    private ImageView imageViewClear;
    private TextView textViewCurrentTripName;
    private ProgressBar progressBarMap;
    private LinearLayout linearLayout;
    private SupportMapFragment supportMapFragment;
    private GoogleMap map;
    private GeoApiContext geoApiContext;
    private PlaceInfoViewAdapter placeInfoViewAdapter;
    private Trip currentTrip;
    private Place currentPlace;
    private SharedPreferencesController sharedPreferencesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Mapa");
        initializeComponents();
        this.placeInfoViewAdapter = new PlaceInfoViewAdapter(this, this);
        this.sharedPreferencesController = new SharedPreferencesController(getApplicationContext());
        this.supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.onCreate(savedInstanceState);
        supportMapFragment.getMapAsync(this);
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey("---PLACE HERE YOUR API KEY---")
                .build();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.trips_list:
                openTripListActivity();
                return true;
            case R.id.map_settings:
                openMapSettingsActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnInfoWindowLongClickListener(marker -> {
            Place place = (Place) marker.getTag();
            showStreetViewPanorama(place);
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                currentPlace = (Place) marker.getTag();
                return false;
            }
        });
        this.initializeMap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Timber.d("" + resultCode);
        Timber.d("" + requestCode);
        switch (requestCode) {
            case TRIP_LIST_ACTIVITY_REQUEST:
                if (resultCode == SUCCESS_RESULT_CODE) {
                    handleTripChoice((Trip) data.getSerializableExtra("trip"));
                }
                break;
            case MAP_SETTINGS_ACTIVITY_REQUEST:
                if (resultCode == SUCCESS_RESULT_CODE) {
                    applyMapSettings();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showStreetViewPanorama(Place place) {
        openStreetViewActivity(place);
    }

    private void initializeComponents(){
        this.imageViewClear = findViewById(R.id.imageViewClear);
        this.textViewCurrentTripName = findViewById(R.id.textViewCurrentTripName);
        this.progressBarMap = findViewById(R.id.progressBarMap);
        this.linearLayout = findViewById(R.id.linearLayoutCurrentTrip);
        this.linearLayout.setVisibility(View.INVISIBLE);
        this.progressBarMap.setVisibility(View.INVISIBLE);
        this.imageViewClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTrip = null;
                map.clear();
                linearLayout.setVisibility(View.INVISIBLE);
            }
        });
        this.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPlace != null){
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentPlace.getLatitude(), currentPlace.getLongitude()), 16));
                }
            }
        });
    }

    private void openStreetViewActivity(Place place) {
        Intent intent = new Intent(this, StreetViewActivity.class);
        intent.putExtra("place", place);
        startActivity(intent);
    }

    private void openTripListActivity() {
        Intent intent = new Intent(this, TripListActivity.class);
        startActivityForResult(intent, TRIP_LIST_ACTIVITY_REQUEST);
    }

    private void openMapSettingsActivity() {
        Intent intent = new Intent(this, MapSettingsActivity.class);
        startActivityForResult(intent, MAP_SETTINGS_ACTIVITY_REQUEST);
    }

    private void initializeMap(){
        LatLng wroclaw = new LatLng(51.107883, 17.038538);
        map.setInfoWindowAdapter(this.placeInfoViewAdapter);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(wroclaw, 15));
        map.setMapStyle(new MapStyleOptions(sharedPreferencesController.getMapStyleFromSharedPreferences()));
        map.setMapType(sharedPreferencesController.getMapTypeFromSharedPrefereneces());
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
            }
        });
    }

    private void applyMapSettings() {
        initializeMap();
        if(currentTrip!=null){
            handleTripChoice(currentTrip);
        }
    }

    private void handleTripChoice(Trip trip) {
        this.currentTrip = trip;
        this.currentPlace = currentTrip.getStartPlace();
        this.progressBarMap.setVisibility(View.VISIBLE);
        this.progressBarMap.setIndeterminate(true);
        DirectionsApiRequest req = prepareRequest(trip);
        req.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Timber.d("OK");
                Handler mainHandler = new Handler(getMainLooper());

                Runnable myRunnable = new Runnable() {
                    @Override
                    public void run() {
                        map.clear();
                        initializeMap();
                        drawRoute(result);
                        showPlacesMarkers(trip);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(trip.getStartPlace().getLatitude(), trip.getStartPlace().getLongitude()), 16));
                        progressBarMap.setVisibility(View.INVISIBLE);
                        progressBarMap.setIndeterminate(false);
                        linearLayout.setVisibility(View.VISIBLE);
                        textViewCurrentTripName.setText(trip.getName());
                    }
                };
                mainHandler.post(myRunnable);

            }

            @Override
            public void onFailure(Throwable e) {
                e.printStackTrace();
            }
        });
    }

    private void showPlacesMarkers(Trip trip) {
        addPlaceMarker(trip.getStartPlace());
        addPlaceMarker(trip.getDestinationPlace());


        for(Place place : trip.getPlaceList()){
            addPlaceMarker(place);
        }
    }

    private void addPlaceMarker(Place place){
        Marker marker = map.addMarker(getMarkerOptionsForPlace(place));
        marker.setTag(place);
    }

    private MarkerOptions getMarkerOptionsForPlace(Place place) {
        return new MarkerOptions().position(new LatLng(place.getLatitude(), place.getLongitude())).icon(BitmapDescriptorFactory.fromResource(sharedPreferencesController.getRouteFlagMarkerIdFromSharedPreferences()))
                .title(place.getName());
    }

    private void drawRoute(DirectionsResult result) {
        List<LatLng> path = new ArrayList();
        if (result.routes != null && result.routes.length > 0) {
            DirectionsRoute route = result.routes[0];

            if (route.legs != null) {
                for (int i = 0; i < route.legs.length; i++) {
                    DirectionsLeg leg = route.legs[i];
                    if (leg.steps != null) {
                        for (int j = 0; j < leg.steps.length; j++) {
                            DirectionsStep step = leg.steps[j];
                            if (step.steps != null && step.steps.length > 0) {
                                for (int k = 0; k < step.steps.length; k++) {
                                    DirectionsStep step1 = step.steps[k];
                                    EncodedPolyline points1 = step1.polyline;
                                    if (points1 != null) {
                                        List<com.google.maps.model.LatLng> coords1 = points1.decodePath();
                                        for (com.google.maps.model.LatLng coord1 : coords1) {
                                            path.add(new LatLng(coord1.lat, coord1.lng));
                                        }
                                    }
                                }
                            } else {
                                EncodedPolyline points = step.polyline;
                                if (points != null) {
                                    List<com.google.maps.model.LatLng> coords = points.decodePath();
                                    for (com.google.maps.model.LatLng coord : coords) {
                                        path.add(new LatLng(coord.lat, coord.lng));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


        if (path.size() > 0) {
            PolylineOptions opts = new PolylineOptions().addAll(path).color(sharedPreferencesController.getRouteColorFromSharedPreferences()).width(8);
            map.addPolyline(opts);
        }

    }

    private DirectionsApiRequest prepareRequest(Trip trip) {
        DirectionsApiRequest req = DirectionsApi.getDirections(this.geoApiContext, trip.getStartPlace().getCoordinatesAsString(), trip.getDestinationPlace().getCoordinatesAsString());
        if (trip.getType().equals(Trip.CLASSIC_TRIP)) {
            req = req.mode(TravelMode.WALKING);
        } else {
            req = req.mode(TravelMode.BICYCLING);
        }

        List<String> waypoints = new ArrayList<>();

        for (Place waypoint : trip.getPlaceList()) {
            waypoints.add(waypoint.getCoordinatesAsString()
            );
        }
        req = req.waypoints(waypoints.toArray(new String[waypoints.size()]));
        req.optimizeWaypoints(false);
        return req;
    }
}
