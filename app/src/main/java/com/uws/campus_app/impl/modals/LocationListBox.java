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
package com.uws.campus_app.impl.modals;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.core.modals.BaseListBox;
import com.uws.campus_app.core.modals.OnInputEntered;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.util.Log;

public class LocationListBox extends BaseListBox {
	private OnInputEntered handler;

	public LocationListBox(Activity activity, OnInputEntered box) {
        super(activity);
		handler = box;
	}
	
	public void show(final ArrayAdapter<String> arrayAdapter, AlertDialog.Builder builder) {
        putRoomsFromFileIntoArrayAdapter(arrayAdapter);

        builder.setTitle("Select the location you want to go to");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.onInputEntered(arrayAdapter.getItem(which));
            }
        });

        builder.show();
	}

    private void putRoomsFromFileIntoArrayAdapter(ArrayAdapter<String> arrayAdapter) {
        try {
            InputStream is = UwsCampusApp.getAppContext().getAssets().open("lists/rooms.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while((line = br.readLine()) != null) {
                arrayAdapter.add(line);
            }

            br.close();
            is.close();
        } catch (Exception e) {
            Log.e("putRoomsFromFile", e.getMessage());
        }
    }
}
