package com.witold.mapapp.view.trip_list_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;
import com.witold.mapapp.R;
import com.witold.mapapp.model.Trip;
import com.witold.mapapp.model.TripRepository;
import com.witold.mapapp.view.main_activity.MainActivity;

import java.util.List;

import timber.log.Timber;

public class TripListActivity extends AppCompatActivity implements TripsRecyclerViewAdapter.TripListRecyclerViewHolder {
    private List<Trip> trips;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Katalog wycieczek");
        setContentView(R.layout.activity_trip_list);
        this.recyclerView = findViewById(R.id.recyclerViewTripList);
        this.trips = (new TripRepository()).getAllTrips();
        this.setTripList(this.trips);
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

    private void setTripList(List<Trip> list) {
        TripsRecyclerViewAdapter tripsRecyclerViewAdapter = new TripsRecyclerViewAdapter(list, this, Picasso.get());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tripsRecyclerViewAdapter);
    }

    @Override
    public void showTripOnMap(Trip trip) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("trip", trip);
        intent.putExtras(bundle);
        setResult(MainActivity.SUCCESS_RESULT_CODE, intent);
        finish();
    }
}
