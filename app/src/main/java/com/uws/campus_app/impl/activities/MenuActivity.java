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

import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;

import com.uws.campus_app.R;

@SuppressWarnings("rawtypes")
public class MenuActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_menu);
		
		setupButton(R.id.StudentLifeButton,  StudentLifeActivity.class);
		setupButton(R.id.UtilitiesButtons, UtilitiesActivity.class);
		setupButton(R.id.TransportButton,  TransportActivity.class);
		setupButton(R.id.ContactButton,  ContactActivity.class);
		setupButton(R.id.CampusButton,  CampusActivity.class);
		setupButton(R.id.AboutButton,  AboutActivity.class);		
	}
	
	private void setupButton(Integer ID, final Class act) {
		Button button = (Button)findViewById(ID);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				 startActivity(new Intent(arg0.getContext(), act)); 
				 overridePendingTransition(R.anim.in, R.anim.out);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int key, KeyEvent e) {
		if(key == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(getBaseContext(), SplashActivity.class)); 
			overridePendingTransition(R.anim.in, R.anim.out);
			return true;
		}
		
		return super.onKeyDown(key, e);
	}
}
