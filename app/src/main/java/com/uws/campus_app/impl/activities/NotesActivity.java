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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import com.uws.campus_app.R;

import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.impl.adapters.NoteListAdapter;

@SuppressWarnings("unused")
public class NotesActivity extends ExpandableListActivity {
	private static final String FILENAME = "NOTES.txt";
	private ExpandableListView noteList;
	private List<String> titles;
	private List<String> notes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_notes);
		titles = new ArrayList<String>();
		titles.add("Your notes");
		
		notes = new ArrayList<String>();
		notes.add("Example note");
		
		File file = UwsCampusApp.getAppContext().getFileStreamPath(FILENAME);
		if(file.exists()) {
			try {
	    		FileInputStream inputStream = openFileInput(FILENAME);
	    		Vector<String> fileLines = new Vector<String>();
	    		StringBuffer fileData = new StringBuffer("");
	    		int content;
	    		
	    		while(((content = inputStream.read()) != -1)) {
	    			if((char)content == '\n') {
	    				fileLines.add(fileData.toString().replace("\n", "").replace("\r", ""));
	    				fileData = new StringBuffer("");
	    			} else {
	    				fileData.append(((char)content));
	    			}
	    		}
	    		
	    		for(int i = 0; i < fileLines.size(); i++) {
	    			if(i % 2 == 0) {
	    				titles.add(fileLines.get(i));
	    			} else {
	    				notes.add(fileLines.get(i));
	    			}
	    		}
	    		
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
		} 
		
		noteList = (ExpandableListView)findViewById(android.R.id.list);
		noteList.setAdapter(new NoteListAdapter(this, titles, notes, noteList));
	}
	
	@Override
	public void onStop() {
		super.onStop();
	
		try {
			FileOutputStream outStream = openFileOutput(FILENAME, Context.MODE_PRIVATE);	
			String data = "";
			for(int i = 1; i < titles.size(); i++) {
				String title = titles.get(i);
				String note = notes.get(i);
				
				title = title.replace("\n", "").replace("\r", "");
				note = note.replace("\n", "").replace("\r", "");
				
				outStream.write(title.getBytes());
				outStream.write('\n');
				outStream.write(note.getBytes());
				outStream.write('\n');
			}
	
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
}

