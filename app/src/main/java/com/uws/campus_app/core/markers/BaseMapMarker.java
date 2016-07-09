package com.uws.campus_app.core.markers;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public abstract class BaseMapMarker implements MapMarker {
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MapMarker) {
            MapMarker marker = (MapMarker)obj;
            double lat = marker.getLat();
            double lng = marker.getLng();

            boolean sameTitle = marker.getTitle().compareToIgnoreCase(this.getTitle()) == 0;
            boolean sameLat = Double.compare(lat, this.getLat()) == 0;
            boolean sameLng = Double.compare(lng, this.getLng()) == 0;

            return(sameLat && sameLng && sameTitle);
        } else if(obj instanceof Marker) {
            Marker marker = (Marker)obj;
            boolean samePosition = marker.getPosition().equals(new LatLng(getLat(), getLng()));
            boolean sameTitle = marker.getTitle().compareToIgnoreCase(this.getTitle()) == 0;

            return(samePosition && sameTitle);
        } else {
            return false;
        }
    }
}
