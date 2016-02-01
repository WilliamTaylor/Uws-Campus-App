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
package com.uws.campus_app.impl.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;

import org.json.JSONArray;

import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.core.http.HttpPost;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class ContactTask extends AsyncTask<Void, Void, String> {
	private static String FILENAME = "contact_cache.txt";
	private class UniversityContact {
		public String name;
		public String tel;
        public String email;
		public String office;
	}

	private HashMap<String, UniversityContact> contacts;
	private AutoCompleteTextView completeTextView;
    private ProgressDialog progressDialog;
	private HttpPost httpPost;
    private Context context;

    public ContactTask(Context c) {
		contacts = new HashMap<>();
		httpPost = new HttpPost();
		httpPost.open("http://52.30.3.233:3005/getContacts/");
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading contacts, Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
    	if(UwsCampusApp.isNetworkConnected()) {
			return httpPost.send();
    	} else {
    		try {
	    		FileInputStream inputStream = context.openFileInput(FILENAME);
	    		StringBuilder fileData = new StringBuilder("");
	    		int content;
	    		while(((content = inputStream.read()) != -1)) {
	    			fileData.append(((char)content));
	    		}
	    		
	    		inputStream.close();
	    		return fileData.toString();
    		} catch(Exception e) {
    			e.printStackTrace();
    		}
    	}
    	
    	return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONArray json = new JSONArray(result);
    		for(int i = 0; i < json.length(); i++) {
    			String email = json.getJSONObject(i).getString("email");
				String office = json.getJSONObject(i).getString("office");
                String number = json.getJSONObject(i).getString("tel");
                String name = json.getJSONObject(i).getString("name");

    			if(email.length() > 4 && name.length() > 3 && number.length() > 4) {
	    			if(email.contains("@")) {
						UniversityContact contact = new UniversityContact();
						contact.name = name;
						contact.tel = number;
						contact.office = office;
                        contact.email = email;
						contacts.put(name, contact);
	    			}
    			}
    		}

    		File file = new File(FILENAME);
    		if(!file.exists()) {
				FileOutputStream outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				outputStream.write(result.getBytes());
				outputStream.close();
    		}

            Set<String> keys = contacts.keySet();
            String[] keyArray = keys.toArray(new String[keys.size()]);
    		completeTextView.setAdapter(new ArrayAdapter<>(context ,android.R.layout.simple_dropdown_item_1line, keyArray));
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        progressDialog.hide();
    }

	public void setTextView(AutoCompleteTextView findViewById) {
		completeTextView = findViewById;
	}

	public CharSequence getOfficeNumber(String name) {
        if(contacts.containsKey(name)) {
            return contacts.get(name).office;
        } else {
            return "N/A";
        }
	}

	public CharSequence getPhoneNumber(String name) {
        if(contacts.containsKey(name)) {
            return contacts.get(name).tel;
        } else {
            return "N/A";
        }
	}
    
	public CharSequence getEmail(String name) {
        if(contacts.containsKey(name)) {
            return contacts.get(name).email;
        } else {
            return "N/A";
        }
	}
}
