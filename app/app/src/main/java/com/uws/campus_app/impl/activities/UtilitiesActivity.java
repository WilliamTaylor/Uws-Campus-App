package com.uws.campus_app.impl.activities;

import com.uws.campus_app.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;

public class UtilitiesActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_utilities);

        findViewById(R.id.NotesImage).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), NotesActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        findViewById(R.id.LibraryImage).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.setUrl("http://libcat.uhi.ac.uk/search~S18");
                startActivity(new Intent(getBaseContext(), WebViewActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        findViewById(R.id.LinksImage).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                WebViewActivity.setUrl("https://www.sauws.org.uk/whatson/");
                startActivity(new Intent(getBaseContext(), WebViewActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });

        findViewById(R.id.SupermarketImage).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SupermarketActivity.class));
                overridePendingTransition(R.anim.in, R.anim.out);
            }
        });
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
