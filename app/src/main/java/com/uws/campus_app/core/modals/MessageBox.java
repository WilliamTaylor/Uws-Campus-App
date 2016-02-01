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
package com.uws.campus_app.core.modals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MessageBox {
	private Activity activity;
	private String message = "Yes/No ?";
	private String title = "Title";
	
	public MessageBox(Activity act) {
		activity = act;
	}
	
	public void show(final Activity activity) {
		activity.runOnUiThread(new Runnable() {
	        public void run() {
				new AlertDialog.Builder(activity)
					.setTitle(title)
					.setMessage(message)
					.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					}).show();
	        }
	    });
	}
	
	public void show() {
		if(activity != null) {
			show(activity);
		}
	}
	
	public MessageBox setTitle(String title) {
		if(title != null) {
			this.title = title;
		}

		return this;
	}
	
	public MessageBox setMessage(String message) {
		if(message != null) {
			this.message = message;
		}

		return this;
	}
}
