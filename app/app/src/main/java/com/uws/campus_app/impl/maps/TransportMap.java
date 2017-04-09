package com.uws.campus_app.impl.maps;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.core.maps.BaseCustomMap;
import com.uws.campus_app.core.maps.CustomMap;
import com.uws.campus_app.core.markers.MapMarker;
import com.uws.campus_app.impl.markers.TransportMapMarker;
import com.uws.campus_app.impl.tasks.RouteTask;

@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("unused")
public class TransportMap extends BaseCustomMap implements CustomMap {
    public static final String TRAIN_MARKER = "TrainMarker";
    public static final String BUS_MARKER = "BusMarker";

    public static final Float CENTER_LAT = 55.843542F;
    public static final Float CENTER_LON = -4.429995F;
    public static final Float ZOOM = 17.0F;

    private GoogleMap googleMap;
    private Integer tapCounter;
    private String lastMarker;
    private Activity activity;
    private Context context;

    public TransportMap(Activity activity) {
        super(activity);
        tapCounter = 0;
    }

    public void prepareStations(Activity a) {
        registerMarker(new TransportMapMarker("Paisley Gilmour Street Station", new LatLng(55.847349F, -4.424475F), TRAIN_MARKER, activity));
        registerMarker(new TransportMapMarker("Canal Street Station", new LatLng(55.840181F, -4.423918F), TRAIN_MARKER, activity));
        registerMarker(new TransportMapMarker("Paisley University Bus Stop", new LatLng(55.844512F, -4.430832F), BUS_MARKER, activity));
        registerMarker(new TransportMapMarker("New Street Bus Stop", new LatLng(55.843747F, -4.425994F), BUS_MARKER, activity));
    }

    public void setup(GoogleMap map, Context c, Activity activity) {
        this.activity = activity;
        this.context = c;

        prepareStations(activity);

        if(map != null) {
            applyMapSettings(map);

            googleMap = map;
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            googleMap.setOnMarkerClickListener(this);
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                    new CameraPosition.Builder()
                            .target(new LatLng(CENTER_LAT, CENTER_LON))
                            .zoom(ZOOM)
                            .bearing(0)
                            .tilt(45)
                            .build()
            ));
        }
    }

    public void showBusPath() {
        clearMap();
        placeUser();

        List<Marker> onScreenMarkers = getOnScreenMarkers();
        for(MapMarker marker : getMarkers()) {
            if(marker.getType().compareToIgnoreCase(BUS_MARKER) == 0) {
                onScreenMarkers.add(marker.insertOnMap(googleMap));
            }
        }

        double lat = getUserLatitude();
        double lng = getUserLongitude();

        String busSpot1 = RouteTask.makeURL(getUserLatitude(), getUserLongitude(), 55.844512F, -4.430832F);  // High Streen bus stop left
        String busSpot2 = RouteTask.makeURL(getUserLatitude(), getUserLongitude(), 55.843753F, -4.425972F);  // Storie street bus stop

        RouteTask task1 = new RouteTask(busSpot1, context, googleMap, this);
        RouteTask task2 = new RouteTask(busSpot2, context, googleMap, this);

        if(!UwsCampusApp.locationAndInternetAvailable()) {
            task1.setCacheFile("cache/station/new street bus stop.json");
            task2.setCacheFile("cache/station/paisley university bus stop.json");
        }

        task1.execute();
        task2.execute();
    }

    public void showTrainPath() {
        clearMap();
        placeUser();

        List<Marker> onScreenMarkers = getOnScreenMarkers();
        for(MapMarker marker : getMarkers()) {
            String type = marker.getType();
            if(type.compareToIgnoreCase(TRAIN_MARKER) == 0) {
                onScreenMarkers.add(marker.insertOnMap(googleMap));
            }
        }

        String trainSpot1 = RouteTask.makeURL(getUserLatitude(), getUserLongitude(), 55.847012F, -4.424374F);
        String trainSpot2 = RouteTask.makeURL(getUserLatitude(), getUserLongitude(), 55.840127F, -4.424588F);

        RouteTask task1 = new RouteTask(trainSpot1, context, googleMap, this);
        RouteTask task2 = new RouteTask(trainSpot2, context, googleMap, this);

        if(!UwsCampusApp.locationAndInternetAvailable()) {
            task1.setCacheFile("cache/station/paisley gilmour street station.json");
            task2.setCacheFile("cache/station/canel street station.json");
        }

        task1.execute();
        task2.execute();
    }

    @Override
    public GoogleMap getMap() {
        return this.googleMap;
    }
}
