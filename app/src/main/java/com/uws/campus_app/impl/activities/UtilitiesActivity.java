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
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class UtilitiesActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_utilities);
		
		findViewById(R.id.NotesImage).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getBaseContext(), NotesActivity.class));
				overridePendingTransition(R.anim.in, R.anim.out);
			}
		});
		
		findViewById(R.id.LibraryImage).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				WebViewActivity.setUrl("http://libcat.uhi.ac.uk/search~S18");
				startActivity(new Intent(getBaseContext(), WebViewActivity.class));
				overridePendingTransition(R.anim.in, R.anim.out);
			}
		});
		
		findViewById(R.id.LinksImage).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				WebViewActivity.setUrl("https://www.sauws.org.uk/whatson/");
				startActivity(new Intent(getBaseContext(), WebViewActivity.class));
				overridePendingTransition(R.anim.in, R.anim.out);
			}
		});
		
		findViewById(R.id.SupermarketImage).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getBaseContext(), SupermarketActivity.class));
				overridePendingTransition(R.anim.in, R.anim.out);
			}
		});
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
