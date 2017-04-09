package com.uws.campus_app.impl.activities;

import com.uws.campus_app.R;

import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

public class SplashActivity extends Activity  {	    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction() == MotionEvent.ACTION_DOWN) {
            LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                  AlertDialog.Builder builder = new AlertDialog.Builder(this);

                  builder.setTitle("Your Location Services Are Off");
                  builder.setMessage("Please enable Location Services otherwise we wont be able to give you directions :) ");
                  builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                      }
                    }
                  );

                  builder.setNegativeButton("Continue Anyway", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialogInterface, int i) {
                          startActivity(new Intent(getBaseContext(), MenuActivity.class));
                          overridePendingTransition(R.anim.in, R.anim.out);
                      }
                    }
                  );

                  Dialog alertDialog = builder.create();
                  alertDialog.setCanceledOnTouchOutside(false);
                  alertDialog.show();
            } else {
                startActivity(new Intent(getBaseContext(), MenuActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int key, KeyEvent e) {
        if(key == KeyEvent.KEYCODE_BACK) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            return true;
        }

        return super.onKeyDown(key, e);
    }
}
