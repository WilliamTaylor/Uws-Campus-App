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
package com.uws.campus_app.impl.files;

import com.uws.campus_app.core.files.File;

public class AboutFile {
	private static final String LOCATION = "data/about.txt";
	private String aboutText;
	private File file;
	
	public AboutFile() {
		file = new File(LOCATION);
		file.open();
	}
	
	public void load() {
        aboutText = file.getContents();
	}
	
	public String getAboutText() {
		return aboutText;
	}
}
