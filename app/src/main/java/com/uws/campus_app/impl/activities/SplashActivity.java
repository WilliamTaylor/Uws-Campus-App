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
