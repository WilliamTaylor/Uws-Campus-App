package com.uws.campus_app.impl.maps;

import com.google.android.gms.maps.model.*;
import com.uws.campus_app.core.maps.BaseCustomMap;
import com.uws.campus_app.core.maps.CustomMap;
import com.uws.campus_app.core.maps.MarkerInfo;
import com.uws.campus_app.core.markers.MapMarker;
import com.uws.campus_app.core.markers.SimpleMapMarker;
import com.uws.campus_app.impl.tasks.*;
import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.impl.markers.*;
import com.uws.campus_app.core.utilities.*;
import com.google.android.gms.maps.*;

import android.content.Context;
import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

public class UniversityMap extends BaseCustomMap implements CustomMap {
    public static final String BUILDING_MARKERS = "building";
    public static final String ENTRANCE_MARKERS = "entrance";
    public static final String PARKING_MARKERS = "parking";
    public static final String PLACE_MARKERS = "places";
    public static final Float CENTER_LAT = 55.843542F;
    public static final Float CENTER_LON = -4.429995F;
    public static final Float ZOOM = 17.0F;

    private GoogleMap googleMap;
    private Activity activity;
    private Context context;

    public UniversityMap(Activity activity) {
        super(activity);
    }

    public void setup(GoogleMap map, Context context, Activity activity) {
        this.activity = activity;
        this.context = context;

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

    private void placeMarkers(String filename, float color) {
        ArrayList<MarkerInfo> markers = getMarkersFromFile(filename);
        for(MarkerInfo info : markers) {
            LatLng position = new LatLng(info.lat, info.lng);
            registerMarker(new SimpleMapMarker(info.title, position, info.id, color));
        }
    }

    private void placeBuildingMarkers(String filename, float color) {
        ArrayList<MarkerInfo> markers = getMarkersFromFile(filename);
        for(MarkerInfo info : markers) {
            BuildingMapMarker buildingMapMarker = new BuildingMapMarker(info.title, new LatLng(info.lat, info.lng));
            for(MarkerInfo child : info.childMarkers) {
                LatLng position = new LatLng(child.lat, child.lng);
                buildingMapMarker.plotEntrance(new SimpleMapMarker(child.title, position, child.id, color));
            }

            registerMarker(buildingMapMarker);
        }
    }

    public void createMarkers() {
        placeBuildingMarkers("markers/building-markers.json", BitmapDescriptorFactory.HUE_GREEN);
        placeMarkers("markers/entrance-markers.json", BitmapDescriptorFactory.HUE_CYAN);
        placeMarkers("markers/parking-markers.json", BitmapDescriptorFactory.HUE_ROSE);
        placeMarkers("markers/place-markers.json", BitmapDescriptorFactory.HUE_YELLOW);
    }

    @Override
    public GoogleMap getMap() {
        return this.googleMap;
    }

    public void plotRouteTo(RoomName room) {
        char index = room.getRoomCharacter();

        BuildingMapMarker buildingMapMarker = null;
        for(MapMarker buildings : getMarkers()) {
            if(buildings instanceof BuildingMapMarker) {
                BuildingMapMarker marker = ((BuildingMapMarker)buildings);
                if(index == marker.getBuildingCharacter()) {
                    buildingMapMarker = marker;
                    break;
                }
            }
        }

        if(buildingMapMarker != null) {
            plotRouteTo(buildingMapMarker.getTitle());
        }
    }

    public void plotRouteTo(String name) {
        for (MapMarker marker : getMarkers()) {
            if (marker.getTitle().compareToIgnoreCase(name) == 0) {
                double lat = getUserLatitude();
                double lng = getUserLongitude();

                Log.i("Lat", String.valueOf(lat));
                Log.i("Lng", String.valueOf(lng));

                clearMap();

                if (UwsCampusApp.locationAndInternetAvailable()) {
                    String destination = RouteTask.makeURL(lat, lng, marker.getLat(), marker.getLng());
                    RouteTask route = new RouteTask(destination, context, this, this);
                    route.execute();
                } else {
                    String markerName = name;
                    String type = marker.getType();
                    int sz = 2;

                    if(marker instanceof BuildingMapMarker) {
                        BuildingMapMarker buildingMapMarker = (BuildingMapMarker)marker;
                        sz *= buildingMapMarker.getEntrances().size();
                        markerName = String.valueOf(buildingMapMarker.getBuildingCharacter());
                    }

                    for (int i = 0; i < sz; i++) {
                        RouteTask route = new RouteTask("", context, this, this);
                        route.setCacheFile("cache/" + type + "/cache_" + markerName + String.valueOf(i + 1) + ".json");
                        Log.i("filename", "cache/" + type + "/cache_" + markerName + String.valueOf(i + 1) + ".json");
                        route.execute();
                    }

                    showLocationError();
                }

                placeDestination(marker.getLat(), marker.getLng());
                placeUser();
                break;
            }
        }
    }
}
