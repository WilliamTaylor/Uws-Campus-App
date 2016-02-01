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
package com.uws.campus_app.core.utilities;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.uws.campus_app.UwsCampusApp;

public class RoomName {
	private String[] buildings;
	private String name;
	private final char[] buildingLetters = {
		'A', 'B', 'C', 'D', 'F', 'E',
		'T', 'R', 'G', 'L', 'J', 'H',
		'P', 'M'
	};
	
	public RoomName(String name) {
		this.name = name;
	}
	
	public boolean isRoomNumber() {
		if(name.length() == 4 || (name.length() == 5 && Character.isDigit(name.charAt(4)))) {
			for(int z = 0; z < buildingLetters.length; z++) {
				if(Character.toUpperCase(name.charAt(0)) == buildingLetters[z]) {
					int count = 0;	
					for(int i = 1; i < 4; i++) {
						if(Character.isDigit(name.charAt(i))) {
							count++;
						}
					}
					
					if(count == 3)
                        return true;
				}
			}
		}

		return false;
	}

	public boolean isRoomName() {
		ArrayList<String> lines = new ArrayList<>();
		try {
            InputStream is = UwsCampusApp.getAppContext().getAssets().open("lists/rooms.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

        	String line;
        	while((line = br.readLine()) != null) {
        	    lines.add(line);
        	}

            is.close();
        	br.close();
        } catch (Exception e) {
        	 Log.e("RoomName ", e.getMessage());
        }

		Boolean found = false;
        buildings = new String[lines.size()];
        for(int i = 0; i < buildings.length; i++) {
        	buildings[i] = lines.get(i);
        	if(buildings[i].compareToIgnoreCase(this.name) == 0) {
        		found = true;
        	}
        }
        
		return(found);
	}

	public char getRoomCharacter() {
		if(isRoomNumber()) {
			return Character.toUpperCase(name.charAt(0));
		}

		return 0;
	}

	public int getFloorNumber() {
		return Character.getNumericValue(name.charAt(1));
	}
}
