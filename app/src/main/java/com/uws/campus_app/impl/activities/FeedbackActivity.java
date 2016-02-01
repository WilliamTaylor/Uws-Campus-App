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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;

import com.uws.campus_app.R;

public class FeedbackActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_feedback);
   
		findViewById(R.id.EmailButton).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent email = new Intent(Intent.ACTION_SEND);
				
				EditText suggestionsBox = ((EditText)findViewById(R.id.SuggestionsText));
				EditText reviewBox = ((EditText)findViewById(R.id.ReviewText));
				SeekBar reviewBar = ((SeekBar)findViewById(R.id.ReviewValue));
				
				String suggestions = "Suggestions: " + suggestionsBox.getText() + "\n\n\n";
				String message = " Review: " + reviewBox.getText() + "\n\n\n";
				String value = String.valueOf(reviewBar.getProgress());
				
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{"wi11berto@yahoo.co.uk"});
				email.putExtra(Intent.EXTRA_SUBJECT, "Campus App Review : " + value);
				email.putExtra(Intent.EXTRA_TEXT, suggestions + message);
				email.setType("message/rfc822");
				
				startActivity(Intent.createChooser(email, "Choose an Email client :"));
				
				suggestionsBox.setText("");
				reviewBox.setText("");
			}
		});
		
		((SeekBar)findViewById(R.id.ReviewValue)).setProgress(50);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	     if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	 startActivity(new Intent(getBaseContext(), AboutActivity.class)); 
	    	 overridePendingTransition(R.anim.in, R.anim.out);
	    	 return true;
	     }
	     return super.onKeyDown(keyCode, event);    
	}
}
