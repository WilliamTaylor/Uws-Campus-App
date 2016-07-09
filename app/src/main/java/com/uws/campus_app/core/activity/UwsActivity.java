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
