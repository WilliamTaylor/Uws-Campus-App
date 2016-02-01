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

