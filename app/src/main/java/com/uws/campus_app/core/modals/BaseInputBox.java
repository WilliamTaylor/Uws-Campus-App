package com.uws.campus_app.core.modals;

import android.app.Activity;
import android.app.AlertDialog;

public abstract class BaseInputBox {
    private AlertDialog.Builder builder;
    private OnInputEntered handler;
    private Activity activity;

    public BaseInputBox(Activity activity) {
        this.builder = new AlertDialog.Builder(activity);
        this.activity = activity;
    }

    public BaseInputBox onInputEntered(OnInputEntered handler) {
        if(handler != null) {
           this.handler = handler;
        } else {
            throw new IllegalArgumentException("Handler cannot be null");
        }

        return this;
    }

    public void show() {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                show(builder, activity, handler);
            }
        });
    }

    public abstract void show(final AlertDialog.Builder builder, final Activity activity, final OnInputEntered handler);
}
