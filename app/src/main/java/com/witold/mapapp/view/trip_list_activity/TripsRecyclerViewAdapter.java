package com.witold.mapapp.view.trip_list_activity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.witold.mapapp.R;
import com.witold.mapapp.model.Trip;

import org.w3c.dom.Text;

import java.util.List;


public class TripsRecyclerViewAdapter extends RecyclerView.Adapter<TripsRecyclerViewAdapter.TripsViewHolder> {

    private List<Trip> tripList;
    private TripListRecyclerViewHolder holder;
    private Picasso picasso;

    class TripsViewHolder extends RecyclerView.ViewHolder {
        private Trip trip;
        private TextView textViewTripType;
        private TextView textViewtripCardEstimatedTime;
        private TextView textViewTripName;
        private TextView textViewTripDescription;
        private ImageView imageViewTripImage;
        private Button buttonShowTripOnMap;

        public TripsViewHolder(View itemView) {
            super(itemView);
            this.textViewTripName = itemView.findViewById(R.id.tripCardName);
            this.textViewtripCardEstimatedTime = itemView.findViewById(R.id.tripCardEstimatedTime);
            this.textViewTripType = itemView.findViewById(R.id.tripCardType);
            this.textViewTripDescription = itemView.findViewById(R.id.tripCardDescription);
            this.buttonShowTripOnMap = itemView.findViewById(R.id.tripCardButtonShowOnMap);
            this.imageViewTripImage = itemView.findViewById(R.id.tripCardImage);
        }

        public void bindTrip(final Trip trip) {
            this.trip = trip;
            this.textViewTripType.setText(trip.getType());
            this.textViewtripCardEstimatedTime.setText(String.valueOf(trip.getEstimatedTime()));
            this.textViewTripName.setText(trip.getName());
            this.textViewTripDescription.setText(trip.getDescription());
            picasso.load(trip.getImagePlaceholder()).into(this.imageViewTripImage);
            this.buttonShowTripOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.showTripOnMap(trip);
                }
            });
        }
    }

    public TripsRecyclerViewAdapter(List<Trip> tripList, TripListRecyclerViewHolder holder, Picasso picasso) {
        super();
        this.tripList = tripList;
        this.holder = holder;
        this.picasso = picasso;
    }

    @Override
    public TripsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_trip, parent, false);
        return new TripsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripsViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.bindTrip(trip);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    interface TripListRecyclerViewHolder {
        void showTripOnMap(Trip trip);
    }
}
