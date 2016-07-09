package com.uws.campus_app.impl.adapters;

import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uws.campus_app.R;
import com.uws.campus_app.UwsCampusApp;

@SuppressLint("InflateParams")
public class NoteListAdapter extends BaseExpandableListAdapter {
    private ExpandableListView expandableList;
    private List<String> eventsDetails;
    private List<String> eventTitles;
    private Activity context;

    public NoteListAdapter(Activity context, List<String> titles, List<String> details, ExpandableListView listView) {
        this.expandableList = listView;
        this.eventsDetails = details;
        this.eventTitles = titles;
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String eventTitle = (String)getGroup(groupPosition);
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.note_item, null);
        }

        TextView item = (TextView)convertView.findViewById(R.id.note_title);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(eventTitle);

        final int number = groupPosition;
        convertView.findViewById(R.id.AddButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        LinearLayout layout = new LinearLayout(context);
                        layout.setOrientation(LinearLayout.VERTICAL);

                        final EditText titleBox = new EditText(context);
                        titleBox.setHint("Note title");
                        layout.addView(titleBox);

                        final EditText descriptionBox = new EditText(context);
                        descriptionBox.setHint("Note description");
                        layout.addView(descriptionBox);

                        builder.setView(layout);
                        builder.setTitle("Enter a new note");
                        builder.setCancelable(true);
                        builder.setPositiveButton("Add note", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  if(titleBox.getText().length() > 0 && descriptionBox.getText().length() > 0) {
                                      eventsDetails.add(descriptionBox.getText().toString());
                                      eventTitles.add(titleBox.getText().toString());
                                      dialog.dismiss();
                                  } else {
                                      Toast.makeText(UwsCampusApp.getAppContext(), "Please enter a title and a description", Toast.LENGTH_LONG).show();
                                  }
                              }
                        });

                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        builder.create().show();
                    }
                });
            }
        });

        convertView.findViewById(R.id.RemoveButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventsDetails.size() >= 2 && number != 0) {
                    eventsDetails.remove(number);
                    eventTitles.remove(number);
                    notifyDataSetChanged();
                    collapseAll();
                }
            }
        });

        convertView.findViewById(R.id.Up).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventsDetails.size() >= 2 && number != 0 && number != 1) {
                    Collections.swap(eventsDetails, number, number-1);
                    Collections.swap(eventTitles, number, number-1);
                    notifyDataSetChanged();
                    collapseAll();
                }
            }
        });

        convertView.findViewById(R.id.Down).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(eventsDetails.size() >= 2 && number != eventsDetails.size()-1 && number != 0) {
                    Collections.swap(eventsDetails, number, number+1);
                    Collections.swap(eventTitles, number, number+1);
                    notifyDataSetChanged();
                    collapseAll();
                }
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        String eventDetails = (String)getChild(groupPosition, 0);
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.note_item_description, null);
        }

        if(eventDetails != null) {
            TextView item = (TextView)convertView.findViewById(R.id.details);
            item.setText(eventDetails);
        }
        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return eventTitles.get(groupPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return eventsDetails.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return eventTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return(true);
    }

    private void collapseAll() {
        Integer count = getGroupCount();
        for (int i = 0; i < count; i++){
            expandableList.collapseGroup(i);
        }
    }
}

