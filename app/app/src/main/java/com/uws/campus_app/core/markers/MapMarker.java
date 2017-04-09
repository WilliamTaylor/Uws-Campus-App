package com.uws.campus_app.core.markers;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public interface MapMarker {
    Boolean onClick(Marker data, GoogleMap map);
    Marker insertOnMap(GoogleMap map);

    String getTitle();
    String getType();

    Double getLat();
    Double getLng();
}
