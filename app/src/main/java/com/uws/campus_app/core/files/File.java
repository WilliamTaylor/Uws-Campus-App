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
