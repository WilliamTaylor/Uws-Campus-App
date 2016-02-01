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

import com.uws.campus_app.impl.tasks.EventsTask;
import android.app.ExpandableListActivity;
import android.widget.ExpandableListView;
import android.view.WindowManager;
import android.content.Intent;
import android.view.KeyEvent;
import com.uws.campus_app.R;
import android.os.Bundle;

public class StudentLifeActivity extends ExpandableListActivity {	
	private ExpandableListView eventList;
	private EventsTask task;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.getActionBar().setTitle("UWS News & Events");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_studentlife);
		eventList = (ExpandableListView)findViewById(android.R.id.list);
		task = new EventsTask(this, this, eventList);
		task.execute();
	}
	
	@Override
	public void onStop() {
		super.onStop();
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

