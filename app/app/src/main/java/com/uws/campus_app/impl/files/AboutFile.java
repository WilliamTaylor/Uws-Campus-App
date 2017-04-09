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
