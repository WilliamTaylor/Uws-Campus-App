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
package com.uws.campus_app.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import com.uws.campus_app.R;
import com.uws.campus_app.core.modals.MessageBox;

public abstract class UwsActivity extends Activity {
    private Class<?> activityInfo;
    private Integer backKeycode;

    public UwsActivity(Class<?> activity, int keycode) {
        activityInfo = activity;
        backKeycode = keycode;
    }

    public UwsActivity(Class<?> activity) {
        this(activity, KeyEvent.KEYCODE_BACK);
    }

    protected void transitionTo(Class<?> nextActivity) {
        startActivity(new Intent(getBaseContext(), nextActivity));
        overridePendingTransition(R.anim.in, R.anim.out);
    }

    protected void transitionToActionView(Uri uri, Uri backupUri) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (android.content.ActivityNotFoundException ex) {
            startActivity(new Intent(Intent.ACTION_VIEW, backupUri));
        }

        overridePendingTransition(R.anim.in, R.anim.out);
    }

    protected void showMessageBox(String title, String text){
        new MessageBox(this)
                .setTitle(title)
                .setMessage(text)
                .show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == backKeycode) {
            transitionTo(activityInfo);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
