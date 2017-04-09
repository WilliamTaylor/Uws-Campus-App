/**
 *
 * Copyright 2015 : William Taylor : wi11berto@yahoo.co.uk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.uws.campus_app.core.maps;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.maps.model.PolylineOptions;
import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.core.files.File;
import com.uws.campus_app.core.markers.MapMarker;
import com.uws.campus_app.core.modals.MessageBox;
import com.uws.campus_app.core.tasks.PathTaskFinished;
import com.uws.campus_app.core.location.UserLocation;
import com.uws.campus_app.core.location.UserMovedInterface;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import android.graphics.Color;
import android.app.*;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCustomMap implements OnMarkerClickListener, CustomMap, UserMovedInterface, PathTaskFinished {
    private UserLocation locationRequest = UwsCampusApp.getLocation();
    private List<Polyline> onMapRouteLines = new ArrayList<>();
    private List<MapMarker> mapMarkers = new ArrayList<>();
    private List<Marker> onMapMarkers = new ArrayList<>();
    private Marker destination;
    private Marker userMarker;
    private Activity activity;
    private Circle userArea;

    public BaseCustomMap(Activity activity) {
        locationRequest.setMap(this);
        this.activity = activity;
    }

    public double getUserLongitude() {
        return locationRequest.getLng();
    }

    public double getUserLatitude() {
        return locationRequest.getLat();
    }

    @Override
    public boolean onMarkerClick(Marker clickedMarker) {
        if (clickedMarker != null) {
            for(MapMarker marker : mapMarkers) {
                if(marker.equals(clickedMarker)) {
                    marker.onClick(clickedMarker, getMap());
                }
            }
        }

        return false;
    }

    @Override
    public void onUserMoved(Double lat, Double lng) {
        if(userMarker != null && userArea != null) {
            userMarker.remove();
            userArea.remove();
        }

        GoogleMap googleMap = getMap();
        userMarker = googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("Your Location")
        );

        userArea = googleMap.addCircle(new CircleOptions()
            .center(new LatLng(lat, lng))
            .strokeColor(Color.argb(180, 7, 205, 227))
            .fillColor(Color.argb(100, 7, 205, 227))
            .radius(30.0)
        );
    }

    @Override
    public void onFinished(List<PolylineOptions> lines) {
        GoogleMap map = getMap();

        for(Polyline line : onMapRouteLines) {
            line.remove();
        }

        onMapRouteLines.clear();
        for(PolylineOptions p : lines) {
            Polyline obj = map.addPolyline(p);
            onMapRouteLines.add(obj);
        }
    }

    protected void showLocationError() {
        MessageBox message = new MessageBox(activity);
        message.setTitle("We cant connect to the internet");
        message.setMessage("As we cant send a request for directions from our server we will show you a route from the west and east entrances that have been stored locally on the device");
        message.show();
    }

    private MarkerInfo getMarkerInfoFromJson(JSONObject marker) {
        MarkerInfo markerInfo = new MarkerInfo();
        try {
            markerInfo.lat = Float.parseFloat(marker.getString("lat"));
            markerInfo.lng = Float.parseFloat(marker.getString("lng"));
            markerInfo.title = marker.getString("title");
            markerInfo.id = marker.getString("id");
            markerInfo.childMarkers = new ArrayList<>();

            JSONArray childMarkers = marker.getJSONArray("childMarkers");
            for(int x = 0; x < childMarkers.length(); x++) {
                markerInfo.childMarkers.add(getMarkerInfoFromJson(childMarkers.getJSONObject(x)));
            }
        } catch(Exception e) {
            Log.e("getMarkerInfoFromJson", e.getMessage());
        }

        return markerInfo;
    }

    protected ArrayList<MarkerInfo> getMarkersFromFile(String filename) {
        ArrayList<MarkerInfo> markers = new ArrayList<>();
        try {
            File file = new File();
            file.open(filename);

            JSONObject json = new JSONObject(file.getContents());
            JSONArray array = json.getJSONArray("markers");
            for(int i = 0; i < array.length(); i++) {
                markers.add(getMarkerInfoFromJson(array.getJSONObject(i)));
            }
        } catch(Exception e) {
            Log.e("Error loading markers", e.getMessage());
        }

        return markers;
    }

    protected List<Marker> getOnScreenMarkers() {
        return onMapMarkers;
    }

    public void clearMap() {
        GoogleMap map = getMap();
        onMapMarkers.clear();
        map.clear();
    }

    public void createMarkers() {

    }

    protected List<MapMarker> getMarkers() {
        return mapMarkers;
    }

    protected void placeDestination(double  lat, double lng) {
        GoogleMap map = getMap();

        if(destination != null) {
            destination.remove();
        }

        destination =  map.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lng))
                .title("Destination"));

        destination.showInfoWindow();
    }

    protected void placeUser() {
        GoogleMap googleMap = getMap();
        if(userMarker == null || userArea == null)
            return;

        userMarker.remove();
        userMarker = googleMap.addMarker(new MarkerOptions()
            .position(userMarker.getPosition())
            .title("Your Location")
        );

        userArea.remove();
        userArea = googleMap.addCircle(new CircleOptions()
            .center(userArea.getCenter())
            .strokeColor(Color.argb(180, 7, 205, 227))
            .fillColor(Color.argb(100, 7, 205, 227))
            .radius(30.0)
        );
    }

    protected void applyMapSettings(GoogleMap map) {
        UiSettings options = map.getUiSettings();

        options.setMapToolbarEnabled(false);
        options.setAllGesturesEnabled(true);
        options.setMyLocationButtonEnabled(false);
        options.setZoomControlsEnabled(false);
        options.setZoomGesturesEnabled(true);
        options.setScrollGesturesEnabled(true);
        options.setCompassEnabled(false);
    }

    protected <T extends MapMarker> void registerMarker(T markerClass) {
        this.mapMarkers.add(markerClass);
    }

    public void placeMarkerOfType(String ID) {
        GoogleMap map = this.getMap();
        map.clear();

        for(Marker marker : onMapMarkers) {
            marker.remove();
        }

        onMapMarkers.clear();
        for(MapMarker marker : mapMarkers) {
            if(marker.getType().compareToIgnoreCase(ID) == 0) {
                onMapMarkers.add(marker.insertOnMap(map));
            }
        }

        placeUser();
    }

    public void switchMapType() {
        GoogleMap googleMap = getMap();
        CameraPosition position = googleMap.getCameraPosition();
        if(googleMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE) {
            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            animateMapCamera(googleMap, position, 45);
        } else {
            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            animateMapCamera(googleMap, position, 0);
        }
    }

    protected void animateMapCamera(GoogleMap map, CameraPosition position, int tilt) {
        map.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
            .target(position.target)
            .zoom(position.zoom)
            .bearing(position.bearing)
            .tilt(tilt)
            .build())
        );
    }
}
