package com.uws.campus_app.impl.activities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.uws.campus_app.R;
import com.google.android.gms.maps.GoogleMap;
import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.impl.maps.ShoppingMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;

public class SupermarketActivity extends Activity implements OnMapReadyCallback {
    private ShoppingMap supermarkerMap;

    @Override
    public void onMapReady(GoogleMap map) {
        supermarkerMap = new ShoppingMap(this);
        supermarkerMap.setup(map, this, this);
        supermarkerMap.createMarkers();

        setupButtonListeners();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_supermarket);

        MapFragment fragment = (MapFragment)getFragmentManager().findFragmentById(R.id.shopMap);
        fragment.getMapAsync(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             startActivity(new Intent(getBaseContext(), UtilitiesActivity.class));
             overridePendingTransition(R.anim.in, R.anim.out);
             return true;
         }
         return super.onKeyDown(keyCode, event);
    }

    private void setupButtonListeners() {
        findViewById(R.id.SwitchMap).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SupermarketActivity.this, android.R.layout.select_dialog_singlechoice);
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(SupermarketActivity.this);
                BufferedReader br;
                InputStream is;

                try {
                    is = UwsCampusApp.getAppContext().getAssets().open("lists/shops.txt");
                    br = new BufferedReader(new InputStreamReader(is));
                    String line ;

                    while((line = br.readLine()) != null) {
                        arrayAdapter.add(line);
                    }

                    br.close();
                    is.close();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }

                builderSingle.setTitle("Select the shop you want to go to");
                builderSingle.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        supermarkerMap.onItemSelected(arrayAdapter.getItem(which));
                        dialog.dismiss();
                    }
                });

                builderSingle.show();
            }
        });

        findViewById(R.id.SupermarketSwitch).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            supermarkerMap.switchMapType();
            }
        });
    }
}

