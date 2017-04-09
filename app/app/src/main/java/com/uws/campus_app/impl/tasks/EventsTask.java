package com.uws.campus_app.impl.tasks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.uws.campus_app.UwsCampusApp;
import com.uws.campus_app.impl.adapters.EventListAdapter;
import com.uws.campus_app.core.http.HttpPost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ExpandableListView;

public class EventsTask extends AsyncTask<Void, Void, String> {
    private static String FILENAME = "events_cache.txt";
    private ExpandableListView eventListView;
    private ProgressDialog progressDialog;
    private HttpPost httpPost;
    private Activity activity;
    private Context context;
    
    public EventsTask(Activity a, Context c, ExpandableListView lv) {
        httpPost = new HttpPost();
        httpPost.open("http://52.30.3.233:3005/getNews/");
        eventListView = lv;
        activity = a;
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading events, Please wait...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        if(UwsCampusApp.isNetworkConnected()) {
            return httpPost.send();
        } else {
            return "";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        List<String> eventTitles = new ArrayList<>();
        List<String> eventDates = new ArrayList<>();
        List<String> eventNews = new ArrayList<>();
             
        if(UwsCampusApp.isNetworkConnected()&& result.compareTo("NULL") != 0) {
            try {
                JSONArray jsonObject = new JSONArray(result);
                for(int i = 0; i < jsonObject.length(); i++) {
                    String timeStamp = jsonObject.getJSONObject(i).getString("timestamp");
                    String news = jsonObject.getJSONObject(i).getString("story");
                    String head = jsonObject.getJSONObject(i).getString("head");

                    if(!head.isEmpty() && !news.isEmpty() && !timeStamp.isEmpty()) {
                        eventDates.add(timeStamp.replace("\n", "").replace("<br/>", ""));
                        eventTitles.add(head.replace("\n", "").replace("<br/>", ""));
                        eventNews.add(news.replace("\n", "").replace("<br/>", ""));
                    }
                }

                eventListView.setAdapter(new EventListAdapter(activity, eventTitles, eventNews, eventDates));
                FileOutputStream outStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
                for(int i = 0; i < eventTitles.size(); i++) {
                    String title = eventTitles.get(i);
                    String time = eventDates.get(i);
                    String news = eventNews.get(i);

                    title = title.replace("\n", "").replace("<br/>", "");
                    time = time.replace("\n", "").replace("<br/>", "");
                    news = news.replace("\n", "").replace("<br/>", "");

                    outStream.write(title.getBytes());
                    outStream.write('\n');
                    outStream.write(time.getBytes());
                    outStream.write('\n');
                    outStream.write(news.getBytes());
                    outStream.write('\n');
                }

                outStream.close();
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else { 
            File file = UwsCampusApp.getAppContext().getFileStreamPath(FILENAME);
            if(file.exists()) {
                try {
                    FileInputStream inputStream = context.openFileInput(FILENAME);
                    ArrayList<String> fileLines = new ArrayList<>();
                    StringBuffer fileData = new StringBuffer("");
                    int content;

                    while(((content = inputStream.read()) != -1)) {
                        if((char)content == '\n') {
                            fileLines.add(fileData.toString());
                            fileData = new StringBuffer("");
                        } else {
                            fileData.append(((char)content));
                        }
                    }

                    if(!fileLines.isEmpty()) {
                        Integer ID = 0;
                        for(int i = 0; i < fileLines.size(); i++) {
                            String data = fileLines.get(i);
                            if(data != null) {
                                switch(ID) {
                                    case 0: eventTitles.add(data); break;
                                    case 1: eventDates.add(data); break;
                                    case 2: eventNews.add(data); break;

                                    default: break;
                                }
                            }

                            ID += 1;
                            if(ID == 3) { ID = 0; }
                        }

                        if(!eventTitles.isEmpty()) {
                            eventListView.setAdapter(new EventListAdapter(activity, eventTitles, eventNews, eventDates));
                        }
                    }

                    inputStream.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        }
        
        progressDialog.hide();
    }
}
