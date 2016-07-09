package com.uws.campus_app.impl.modals;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.uws.campus_app.core.modals.BaseInputBox;
import com.uws.campus_app.core.modals.OnInputEntered;

public class LocationInputBox extends BaseInputBox {
    private String message = "Yes/No ?";
    private String title = "Title";

    public LocationInputBox(Activity activity) {
        super(activity);
    }

    public void show(final AlertDialog.Builder builder, final Activity activity, final OnInputEntered callback) {
        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setWidth(300);

        builder.setMessage(message);
        builder.setTitle(title);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(callback != null) {
                    callback.onInputEntered(input.getText().toString());
                }
            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null) {
                    dialog.cancel();
                }
            }
        });

        builder.setNegativeButton("Locations", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new LocationListBox(activity, callback).show();
            }
        });

        builder.show();
    }

    public LocationInputBox setTitle(String title) {
        this.title = title;
        return this;
    }

    public LocationInputBox setMessage(String message) {
        this.message = message;
        return this;
    }
}
