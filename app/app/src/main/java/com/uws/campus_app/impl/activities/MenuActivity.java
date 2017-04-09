package com.uws.campus_app.impl.activities;

import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.app.Activity;
import android.os.Bundle;
import android.view.*;

import com.uws.campus_app.R;

@SuppressWarnings("rawtypes")
public class MenuActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        setupButton(R.id.StudentLifeButton,  StudentLifeActivity.class);
        setupButton(R.id.UtilitiesButtons, UtilitiesActivity.class);
        setupButton(R.id.TransportButton,  TransportActivity.class);
        setupButton(R.id.ContactButton,  ContactActivity.class);
        setupButton(R.id.CampusButton,  CampusActivity.class);
        setupButton(R.id.AboutButton,  AboutActivity.class);
    }

    private void setupButton(Integer ID, final Class act) {
        Button button = (Button)findViewById(ID);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                 startActivity(new Intent(arg0.getContext(), act));
                 overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
    }

    @Override
    public boolean onKeyDown(int key, KeyEvent e) {
        if(key == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(getBaseContext(), SplashActivity.class));
            overridePendingTransition(R.anim.in, R.anim.out);
            return true;
        }

        return super.onKeyDown(key, e);
    }
}
