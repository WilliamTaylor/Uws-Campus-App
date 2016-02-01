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

import com.uws.campus_app.core.activity.*;
import com.uws.campus_app.impl.files.*;
import com.uws.campus_app.R;
import com.uws.campus_app.*;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class AboutActivity extends UwsActivity {
	private UpdatesFile updatesFile;
	private AboutFile aboutFile;

	public AboutActivity() {
		super(MenuActivity.class);

        updatesFile = new UpdatesFile();
        updatesFile.load();

        aboutFile = new AboutFile();
        aboutFile.load();
	}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        setupGuiElements();
    }

    private void setupGuiElements() {
        TextView aboutText = (TextView)findViewById(R.id.AboutText);
        aboutText.setText(aboutFile.getAboutText());

        ListView updates = (ListView)findViewById(R.id.UpdateList);
        String[] values = updatesFile.getUpdateTitles();

        updates.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, values));
        updates.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int num, long arg3) {
                showMessageBox(updatesFile.getUpdateTitle(num), updatesFile.getUpdateText(num));
            }
        });

        Button rateButton = (Button)findViewById(R.id.RateButton);
        rateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UwsCampusApp.locationAndInternetAvailable()) {
                    Uri backupUri = Uri.parse("http://play.google.com/store/apps/details?id=" + "com.uws.campus_app");
                    transitionToActionView(Uri.parse("market://details?id=" + "com.uws.campus_app"), backupUri);
                }
            }
        });

        Button send = (Button)findViewById(R.id.SendButton);
        send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionTo(FeedbackActivity.class);
            }
        });
    }
}

