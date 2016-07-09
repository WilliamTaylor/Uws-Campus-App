package com.uws.campus_app.impl.activities;

import com.google.android.gms.maps.MapFragment;
import com.uws.campus_app.R;
import com.google.android.gms.maps.GoogleMap;
import com.uws.campus_app.impl.maps.TransportMap;

import com.google.android.gms.maps.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class TransportActivity extends Activity implements OnMapReadyCallback {
    private TransportMap transportMap;
    private ImageView trainButton;
    private ImageView busButton;

    @Override
    public void onMapReady(GoogleMap map) {
        transportMap = new TransportMap(this);
        transportMap.setup(map, this, this);

        trainButton = (ImageView)findViewById(R.id.trainButton);
        trainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                transportMap.showTrainPath();
            }
        });

        findViewById(R.id.TransportSwitchMap).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                transportMap.switchMapType();
            }
        });

        busButton = (ImageView)findViewById(R.id.busButton);
        busButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                transportMap.showBusPath();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transport);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.transportMap);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             startActivity(new Intent(getBaseContext(), MenuActivity.class));
             overridePendingTransition(R.anim.in, R.anim.out);
             return true;
         }

         return super.onKeyDown(keyCode, event);
    }
}

