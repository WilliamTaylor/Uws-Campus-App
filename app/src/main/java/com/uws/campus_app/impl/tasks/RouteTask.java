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
package com.uws.campus_app.impl.tasks;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.uws.campus_app.core.maps.CustomMap;
import com.uws.campus_app.core.http.*;
import com.uws.campus_app.core.tasks.PathTaskFinished;

public class RouteTask extends AsyncTask<Void, Void, String> {	
	private static List<PolylineOptions> polylines = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Integer colour = -1;
    private CustomMap map;
    private PathTaskFinished event;
    private GoogleMap googleMap;
    private String cacheFile;
    private Context context;
    private String url;

    public RouteTask(String url, Context context, CustomMap map, GoogleMap googleMap, PathTaskFinished event) {
        this.colour = Color.BLUE;
        this.googleMap = googleMap;
        this.context = context;
        this.event = event;
        this.map = map;
        this.url = url;
    }

    public RouteTask(String url, Context context, CustomMap map, PathTaskFinished event) {
        this(url, context, map, null, event);
    }
    
    public RouteTask(String url, Context context, GoogleMap map, PathTaskFinished event) {
    	this(url, context, null, map, event);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Fetching route, Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    
    public void drawPath(String result, GoogleMap map) {
        polylines.clear();

	    try {
	        JSONObject json = new JSONObject(result);
	        JSONArray routeArray = json.getJSONArray("routes");
	        JSONObject routes = routeArray.getJSONObject(0);
	        JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
	        
	        String encodedString = overviewPolylines.getString("points");
	        List<LatLng> list = decodePoly(encodedString);
	        for(int z = 0; z < list.size() - 1; z++) {
	            LatLng src = list.get(z);
	            LatLng dest = list.get(z + 1);

            	polylines.add(new PolylineOptions()
                    .add(new LatLng(src.latitude, src.longitude), new LatLng(dest.latitude, dest.longitude))
                    .width(6)
                    .color(colour)
                    .geodesic(false)
				);

				map.addPolyline(polylines.get(z));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private static List<LatLng> decodePoly(String encoded) {
	    List<LatLng> poly = new ArrayList<>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;
	
	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;
	
	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;
	
	        LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
	        poly.add(p);
	    }
	
	    return poly;
	}
	
	public static String makeURL(double sourcelat, double sourcelog, double destlat, double destlog) {
	    StringBuilder urlString = new StringBuilder();
	   
	    urlString.append("http://maps.googleapis.com/maps/api/directions/json");
	    urlString.append("?origin=");
	    urlString.append(Double.toString(sourcelat));
	    urlString.append(",");
	    urlString.append(Double.toString(sourcelog));
	    urlString.append("&destination=");
	    urlString.append(Double.toString(destlat));
	    urlString.append(",");
	    urlString.append(Double.toString(destlog));
	    urlString.append("&sensor=false&mode=walking&alternatives=true");
	    
	    return urlString.toString();
	}

    @Override
    protected String doInBackground(Void... params) {
    	HttpPost postRequest = new HttpPost();
        Log.i("URL", url);
    	if(cacheFile == null) {
            postRequest.open(url);
            return postRequest.send();
    	} else {
            postRequest.open(url, cacheFile);
            return postRequest.get();
    	}
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.hide();
        if (result != null) {
        	if(map != null) {
        		drawPath(result, map.getMap());
        	} else {
        		drawPath(result, googleMap);
        	}
        	
        	if(event != null) {
        		event.onFinished(polylines);
        	}
        }
    }

	public void setCacheFile(String string) {
		this.cacheFile = string;
	}

	public void setRouteColour(Integer c) {
		colour = c;
	}
}
