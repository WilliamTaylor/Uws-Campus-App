package com.uws.campus_app.impl.maps;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.core.maps.BaseCustomMap;
import com.uws.campus_app.core.maps.CustomMap;
import com.uws.campus_app.core.markers.MapMarker;
import com.uws.campus_app.core.markers.SimpleMapMarker;
import com.uws.campus_app.impl.tasks.RouteTask;

@SuppressWarnings("unused")
public class ShoppingMap extends BaseCustomMap implements CustomMap {
    private static final String SHOP = "Shop";

    public static final Float CENTER_LAT = 55.843542F;
    public static final Float CENTER_LON = -4.429995F;
    public static final Float ZOOM = 17.0F;

    private GoogleMap googleMap;
    private Activity activity;
    private Context context;

    public ShoppingMap(Activity activity) {
        super(activity);
    }

    public void setup(GoogleMap map, Context c, Activity activity) {
        this.activity = activity;
        this.context = c;

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

    @Override
    public void createMarkers() {
        registerMarker(new SimpleMapMarker("Morrisons", new LatLng(55.841814, -4.415168), SHOP, BitmapDescriptorFactory.HUE_MAGENTA));
        registerMarker(new SimpleMapMarker("Farmfoods", new LatLng(55.843648, -4.423496), SHOP, BitmapDescriptorFactory.HUE_MAGENTA));
        registerMarker(new SimpleMapMarker("Icelands", new LatLng(55.846557, -4.422311), SHOP, BitmapDescriptorFactory.HUE_MAGENTA));
        registerMarker(new SimpleMapMarker("Game", new LatLng(55.845654, -4.423112), SHOP, BitmapDescriptorFactory.HUE_MAGENTA));
    }

    @Override
    public GoogleMap getMap() {
        return this.googleMap;
    }

    public void onItemSelected(String item) {
        clearMap();

        Double lat = 0.0;
        Double lng = 0.0;
        for(MapMarker marker : getMarkers()) {
            String markerName = marker.getTitle().toLowerCase();
            if(marker.getTitle().compareToIgnoreCase(item) == 0) {
                lat = marker.getLat();
                lng = marker.getLng();

                String destination = RouteTask.makeURL(lat, lng, getUserLatitude(), getUserLongitude());

                if (UwsCampusApp.locationAndInternetAvailable()) {
                    new RouteTask(destination, context, this, this).execute();
                } else {
                    Log.i("FILENAME", "cache/shops/"+markerName+".json");
                    RouteTask route = new RouteTask("", context, this, this);
                    route.setCacheFile("cache/shops/"+markerName+".json");
                    route.execute();

                    showLocationError();
                }

                break;
            }
        }

        super.placeDestination(lat, lng);
        super.placeUser();
    }
}
