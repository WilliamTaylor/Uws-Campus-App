package com.uws.campus_app.impl.markers;

import java.util.*;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uws.campus_app.core.markers.BaseMapMarker;
import com.uws.campus_app.core.markers.MapMarker;

public class BuildingMapMarker extends BaseMapMarker implements MapMarker {
    public final static Float ENTRANCE_MARKER_COLOUR = BitmapDescriptorFactory.HUE_CYAN;
    public final static Float BUILDING_MARKER_COLOUR = BitmapDescriptorFactory.HUE_AZURE;
    public final static String BUILDING_MARKER = "building";

    private List<MapMarker> buildingEntrances;
    private String buildingSnippet;
    private LatLng buildingPoint;
    private String buildingName;

    public BuildingMapMarker(String name, LatLng pos) {
        buildingEntrances = new ArrayList<>();
        buildingSnippet = "";
        buildingPoint = pos;
        buildingName = name;
    }

    public String getTitle() {
        return buildingName;
    }

    public BuildingMapMarker plotEntrance(MapMarker simpleMapMarker) {
        if(simpleMapMarker != null) {
            buildingEntrances.add(simpleMapMarker);
        }

        return this;
    }

    public Marker insertOnMap(GoogleMap map) {
        return map.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.defaultMarker(BUILDING_MARKER_COLOUR))
            .snippet(buildingSnippet)
            .position(buildingPoint)
            .title(buildingName)
        );
    }

    public String getType() {
        return BUILDING_MARKER;
    }

    public List<MapMarker> getEntrances() {
        return buildingEntrances;
    }

    public char getBuildingCharacter() {
        return buildingName.charAt(buildingName.length()-1);
    }

    public Boolean onClick(Marker marker, GoogleMap map) {
        if(buildingName != null && marker != null) {
            if(marker.getTitle().compareToIgnoreCase(buildingName) == 0) {
                map.clear();

                for(MapMarker entrance : buildingEntrances) {
                    entrance.insertOnMap(map);
                }

                map.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.defaultMarker(ENTRANCE_MARKER_COLOUR))
                    .position(buildingPoint)
                    .title(buildingName)
                ).showInfoWindow();
                return true;
            }
        }

        return false;
    }

    public Double getLng() {
        return buildingPoint.longitude;
    }

    public Double getLat() {
        return buildingPoint.latitude;
    }

    public String getName() {
        return buildingName;
    }
}
