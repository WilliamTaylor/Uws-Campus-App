package com.uws.campus_app.impl.files;

import com.uws.campus_app.core.files.File;

import java.util.List;
import java.util.ArrayList;

public class UpdatesFile {
    private class UpdateEntry {
        public String title;
        public String date;
        public String info;
    }

    private static final String LOCATION = "data/updates.txt";
    private List<UpdateEntry> updates;
    private File file;

    public UpdatesFile() {
        updates = new ArrayList<>();
        file = new File(LOCATION);
        file.open();
    }

    public void load() {
        ArrayList<String> fileList = file.getContentsAsList();
        updates.clear();
        for(int i = 0; i < fileList.size(); i++) {
            String string = fileList.get(i);
            if(string.charAt(0) == '#') {
                UpdateEntry entry = new UpdateEntry();

                entry.title = string;
                entry.date = fileList.get(i+1);
                entry.info = fileList.get(i+2);

                updates.add(entry);
            }
        }
    }

    public String[] getUpdateTitles() {
        String[] strings = new String[updates.size()];
        for(int i = 0; i < updates.size(); i++) {
            strings[i] = updates.get(i).title;
        }

        return strings;
    }

    public String getUpdateText(int i) {
        return updates.get(i).info;
    }

    public String getUpdateTitle(int i) {
        return "Update Date : " + updates.get(i).date;
    }
}
