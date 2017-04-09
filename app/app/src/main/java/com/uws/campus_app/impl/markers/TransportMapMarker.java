package com.uws.campus_app.impl.markers;

import android.app.Activity;
import android.app.Dialog;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.webkit.WebViewClient;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.uws.campus_app.R;
import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.core.markers.BaseMapMarker;
import com.uws.campus_app.core.markers.MapMarker;

public class TransportMapMarker extends BaseMapMarker implements MapMarker {
    private static final Float MARKER_COLOUR = BitmapDescriptorFactory.HUE_AZURE;
    private Integer timesHit;
    private Marker instance;
    private LatLng position;
    private Activity context;
    private String title;
    private String id;

    public TransportMapMarker(String title, LatLng pos, String id, Activity con) {
        this.position = pos;
        this.context = con;
        this.timesHit = 0;
        this.title = title;
        this.id = id;
    }

    public Marker insertOnMap(GoogleMap map) {
        instance = map.addMarker(new MarkerOptions()
            .snippet("Click again to see timetable")
                        .title(title)
                        .position(position)
                        .icon(BitmapDescriptorFactory.defaultMarker(MARKER_COLOUR))
        );

        return instance;
    }

    public Double getLat() {
        return position.latitude;
    }

    public Double getLng() {
        return position.longitude;
    }

    public void openLink(String url) {
        final String path = url;
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_web);

                RelativeLayout layout = (RelativeLayout)dialog.findViewById(R.id.webView);

                WebView webView = new WebView(UwsCampusApp.getAppContext());
                webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                webView.setWebViewClient(new WebViewClient());
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

                if(UwsCampusApp.isNetworkConnected()) {
                    webView.loadUrl(path);
                } else {
                    String customHtml = "<html><body><h1>You need an internet connection to view this page </h1></body></html>";
                    webView.loadData(customHtml, "text/html", "UTF-8");
                }

                layout.addView(webView);
                dialog.show();
            }
        });
    }

    @Override
    public Boolean onClick(Marker marker, GoogleMap map) {
        if(instance.equals(marker)) {
            if(++timesHit >= 2) {
                if(marker.getTitle().compareToIgnoreCase("Paisley Gilmour Street Station") == 0) {
                    openLink("http://ojp.nationalrail.co.uk/service/ldbboard/dep/PYG");
                } else if(marker.getTitle().compareToIgnoreCase("Canal Street Station") == 0) {
                    openLink("http://ojp.nationalrail.co.uk/service/ldbboard/dep/PCN");
                } else if(marker.getTitle().compareToIgnoreCase("Paisley University Bus Stop") == 0) {
                    openLink("http://www.nextbuses.mobi/WebView/BusStopSearch/BusStopSearchResults/73924835?currentPage=0");
                } else if(marker.getTitle().compareToIgnoreCase("New Street Bus Stop") == 0) {
                    openLink("http://www.nextbuses.mobi/WebView/BusStopSearch/BusStopSearchResults/73925697?currentPage=0");
                }

                timesHit = 0;
            }

            return true;
        } else {
            timesHit = 0;
            return false;
        }
    }

    @Override
    public String getType() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
