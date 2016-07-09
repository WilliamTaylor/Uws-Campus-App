package com.uws.campus_app.impl.activities;

import java.util.Timer;
import java.util.TimerTask;

import com.uws.campus_app.R;
import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.impl.tasks.ContactTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ContactActivity extends Activity {
    private ContactTask task;
    private String room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        task = new ContactTask(ContactActivity.this);
        task.setTextView((AutoCompleteTextView)findViewById(R.id.contactView));
        task.execute();

        final AutoCompleteTextView acTextView = (AutoCompleteTextView)findViewById(R.id.contactView);
        Button button = (Button)findViewById(R.id.FindRoomOnMap);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(room != null) {
                    startActivity(new Intent(getBaseContext(), CampusActivity.class));
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            CampusActivity.setStartingLocation(room);
                        }
                    }, 2000);
                } else {
                    Toast.makeText(UwsCampusApp.getAppContext(), "You need to select a contact first...", Toast.LENGTH_LONG).show();
                }
            }
        });

        acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = acTextView.getAdapter().getItem(position).toString();
                try {
                    TextView number = (TextView)ContactActivity.this.findViewById(R.id.PhoneNumber);
                    TextView email = (TextView)ContactActivity.this.findViewById(R.id.Email);
                    TextView officeRoom = (TextView)ContactActivity.this.findViewById(R.id.OfficeRoomNumber);

                    officeRoom.setText(task.getOfficeNumber(name));
                    number.setText(task.getPhoneNumber(name));
                    email.setText(task.getEmail(name));

                    room = officeRoom.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        acTextView.setThreshold(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
         if (keyCode == KeyEvent.KEYCODE_BACK) {
             startActivity(new Intent(getBaseContext(), MenuActivity.class));
             overridePendingTransition(R.anim.in, R.anim.out);
             return true;
         }
         return super.onKeyDown(keyCode, event);
    }
}

