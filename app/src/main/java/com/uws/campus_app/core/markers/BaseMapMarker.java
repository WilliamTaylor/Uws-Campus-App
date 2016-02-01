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
