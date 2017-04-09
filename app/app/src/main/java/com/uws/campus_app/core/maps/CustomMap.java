package com.uws.campus_app.core.maps;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.maps.GoogleMap;

public interface CustomMap {
    void setup(GoogleMap map, Context context, Activity activity);
    void createMarkers();

    GoogleMap getMap();
}
