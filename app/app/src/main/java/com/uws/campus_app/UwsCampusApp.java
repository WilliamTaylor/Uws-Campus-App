package com.uws.campus_app;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.uws.campus_app.core.location.UserLocation;

public class UwsCampusApp extends Application {
    private static UserLocation userLocation;
    private static Application instance;
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();

        UwsCampusApp.context = getApplicationContext();
        UwsCampusApp.userLocation = new UserLocation(context);
        UwsCampusApp.instance = this;
    }

    public static UserLocation getLocation() {
        return UwsCampusApp.userLocation;
    }

    public static boolean locationAndInternetAvailable() {
        if(UwsCampusApp.context != null) {
            LocationManager lm = (LocationManager) UwsCampusApp.context.getSystemService(Context.LOCATION_SERVICE);
            if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER) && lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                 ConnectivityManager cm = (ConnectivityManager)instance.getSystemService(Context.CONNECTIVITY_SERVICE);
                 NetworkInfo ni = cm.getActiveNetworkInfo();
                 return(ni != null);
            }
        }

        return false;
    }

    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)instance.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return(ni != null);
    }

    public static Context getAppContext() {
        return UwsCampusApp.context;
    }
}
