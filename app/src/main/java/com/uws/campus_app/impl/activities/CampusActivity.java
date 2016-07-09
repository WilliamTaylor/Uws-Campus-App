package com.uws.campus_app.impl.activities;

import com.uws.campus_app.R;
import com.uws.campus_app.core.activity.UwsActivity;
import com.uws.campus_app.impl.maps.UniversityMap;
import com.uws.campus_app.impl.modals.LocationInputBox;
import com.uws.campus_app.core.modals.OnInputEntered;
import com.uws.campus_app.core.utilities.RoomName;

import com.google.android.gms.maps.*;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class CampusActivity extends UwsActivity implements OnInputEntered, OnMapReadyCallback  {
    private static CampusActivity activity;
    private UniversityMap universityMap;
    private Button showMajorBuildings;
    private Button showParkingAreas;
    private Button showPopularPlaces;
    private Button showEntrances;
    private Button switchMap;
    private Button findRoom;

    public CampusActivity() {
        super(MenuActivity.class);
        activity = this;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        universityMap = new UniversityMap(this);
        universityMap.setup(map, CampusActivity.this, this);
        universityMap.createMarkers();

        showMajorBuildings = (Button)findViewById(R.id.ShowBuildings);
        showPopularPlaces = (Button)findViewById(R.id.ShowSpecial);
        showParkingAreas = (Button)findViewById(R.id.ShowParking);
        showEntrances = (Button)findViewById(R.id.ShowEntrances);
        findRoom = (Button)findViewById(R.id.FindRoomOnMap);
        switchMap = (Button)findViewById(R.id.Switch);

        setupEvents(findRoom, showPopularPlaces, showParkingAreas, showMajorBuildings, switchMap, showEntrances);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_campus);

        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onInputEntered(String arg0) {
        RoomName room = new RoomName(arg0);
        if(room.isRoomNumber()) {
            universityMap.plotRouteTo(room);
            showRoomLevel(room.getFloorNumber());
        } else if(room.isRoomName()) {
            universityMap.plotRouteTo(arg0);
        } else {
            invalidRoom();
        }
    }

    public static void setStartingLocation(String room) {
        final String roomString = room;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.onInputEntered(roomString);
            }
        });
    }

    private void setupEvents(final Button... buttonArray) {
        for(int i = 0; i < buttonArray.length; i++) {
            final Button button = buttonArray[i];
            final OnClickListener event = new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if(button == findRoom) {
                        new LocationInputBox(CampusActivity.this).setTitle("Find a room")
                            .setMessage("Enter the name of a room example : E112, Print Shop")
                            .onInputEntered(activity)
                            .show();
                    } else if(button == showParkingAreas) {
                        universityMap.placeMarkerOfType(UniversityMap.PARKING_MARKERS);
                    } else if(button == showPopularPlaces) {
                        universityMap.placeMarkerOfType(UniversityMap.PLACE_MARKERS);
                    } else if(button == showMajorBuildings) {
                        universityMap.placeMarkerOfType(UniversityMap.BUILDING_MARKERS);
                    } else if(button == showEntrances) {
                        universityMap.placeMarkerOfType(UniversityMap.ENTRANCE_MARKERS);
                    } else {
                        universityMap.switchMapType();
                    }
                }
            };

            button.setOnClickListener(event);
        }
    }

    private void invalidRoom() {
        showMessageBox("Invalid room", "Sorry we don't know that room");
    }

    private void showRoomLevel(Integer level) {
        String msg = "You will find the room on floor " + level.toString() + " in the indicated building."
                + " There will also be maps on the wall that can help you locate the location of the room "
                + "inside the indicated building.";
        showMessageBox("Room Floor", msg);
    }
}

