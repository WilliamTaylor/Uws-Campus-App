package com.uws.campus_app.core.modals;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.ArrayAdapter;

public abstract class BaseListBox {
    private Activity activity;

    public BaseListBox(Activity activity) {
        this.activity = activity;
    }

    public void show() {
        show(new ArrayAdapter<String>(activity, android.R.layout.select_dialog_singlechoice), new AlertDialog.Builder(activity));
    }

    public abstract void show(ArrayAdapter<String> arrayAdapter, AlertDialog.Builder builder);
}
