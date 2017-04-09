package com.uws.campus_app.core.markers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SimpleMapMarker extends BaseMapMarker implements MapMarker {
    private LatLng markerLocation;
    private String markerType;
    private String markerName;
    private String markerText;
    private Float markerColour;

    public SimpleMapMarker(String name, String text, LatLng pos, String type, Float colour) {
        markerLocation = pos;
        markerColour = colour;
        markerText = text;
        markerName = name;
        markerType = type;
    }

    public SimpleMapMarker(String name, LatLng pos, String type, Float colour) {
        this(name, "", pos, type, colour);
    }

    public String getType() {
        return markerType;
    }

    public Marker insertOnMap(GoogleMap map) {
        return map.addMarker(new MarkerOptions()
            .title(markerName)
            .position(markerLocation)
            .icon(BitmapDescriptorFactory.defaultMarker(markerColour))
        );
    }

    public String getTitle() {
        return markerName;
    }

    public Double getLat() {
        return markerLocation.latitude;
    }

    public Double getLng() {
        return markerLocation.longitude;
    }

    @Override
    public Boolean onClick(Marker arg0, GoogleMap map) {
        return false;
    }
}
