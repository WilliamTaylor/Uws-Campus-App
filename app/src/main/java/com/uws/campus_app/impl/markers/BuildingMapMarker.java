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
