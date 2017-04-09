package com.uws.campus_app.impl.activities;

import com.uws.campus_app.impl.tasks.EventsTask;
import android.app.ExpandableListActivity;
import android.widget.ExpandableListView;
import android.view.WindowManager;
import android.content.Intent;
import android.view.KeyEvent;
import com.uws.campus_app.R;
import android.os.Bundle;

public class StudentLifeActivity extends ExpandableListActivity {	
    private ExpandableListView eventList;
    private EventsTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.getActionBar().setTitle("UWS News & Events");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studentlife);
        eventList = (ExpandableListView)findViewById(android.R.id.list);
        task = new EventsTask(this, this, eventList);
        task.execute();
    }

    @Override
    public void onStop() {
        super.onStop();
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

