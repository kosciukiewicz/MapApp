package com.witold.mapapp.view.main_activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;
import com.witold.mapapp.R;
import com.witold.mapapp.model.Place;

public class PlaceInfoViewAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private PlaceInfoViewHolder holder;

    public PlaceInfoViewAdapter(Context context, PlaceInfoViewHolder placeInfoViewHolder) {
        super();
        this.context = context;
        this.holder = placeInfoViewHolder;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.layout_place_info_window, null);
        Place place = (Place) marker.getTag();

        TextView textViewDescription = view.findViewById(R.id.textViewPlaceDescription);
        TextView textViewName = view.findViewById(R.id.textViewPlaceName);
        ImageView imageViewPlaceholder = view.findViewById(R.id.imageViewPlacePlaceHolder);
        textViewName.setText(place.getName());
        textViewDescription.setText(place.getDescription());
        imageViewPlaceholder.setImageDrawable(ContextCompat.getDrawable(context, place.getDrawableId()));
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
