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
package com.uws.campus_app.core.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.util.Log;

import com.uws.campus_app.UwsCampusApp;

public class File {
    private static final AssetManager manager = UwsCampusApp.getAppContext().getAssets();
    private InputStream fileStream;
    private String filename;

    public File(String filename) {
        this.filename = filename;
    }

    public File() {
        this("");
    }

    public Boolean open(String fn) {
        try {
            fileStream = manager.open(fn);
            return true;
        } catch(IOException e) {
            Log.e("File class", e.getMessage());
            return false;
        }
    }

    public Boolean open() {
        return open(filename);
    }

    public InputStreamReader getFileInputStreamReader() {
        return new InputStreamReader(fileStream);
    }

    public InputStreamReader getFileInputStreamReader(String iso) {
        try {
            return new InputStreamReader(fileStream, iso);
        } catch(UnsupportedEncodingException e) {
            Log.e("File", e.getMessage());
            return null;
        }
    }

    public BufferedReader getBufferedInputStream() {
        return new BufferedReader(getFileInputStreamReader());
    }

    public ArrayList<String> getContentsAsList() {
        ArrayList<String> fileLines = new ArrayList<>(50);
        try {
            BufferedReader reader = getBufferedInputStream();
            String fileLine;
            while((fileLine = reader.readLine()) != null) {
                fileLines.add(fileLine);
            }

            reader.close();
        } catch(IOException e) {
            Log.e("File class", e.getMessage());
        }

        return fileLines;
    }

    public String getContents() {
        String contents = "";
        try {
            BufferedReader reader = getBufferedInputStream();
            String fileLine;
            while((fileLine = reader.readLine()) != null) {
                contents += fileLine + " ";
            }

            reader.close();
        } catch(IOException e) {
            Log.e("File class", e.getMessage());
        }

        return contents;
    }
}
