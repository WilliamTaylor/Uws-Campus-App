package com.uws.campus_app.impl.adapters;

import java.util.List;

import com.uws.campus_app.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class EventListAdapter extends BaseExpandableListAdapter {
    private List<String> eventsPostDates;
    private List<String> eventsDetails;
    private List<String> eventTitles;
    private Activity context;

    public EventListAdapter(Activity context, List<String> titles, List<String> details, List<String> dates) {
        this.eventsPostDates = dates;
        this.eventsDetails = details;
        this.eventTitles = titles;
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String eventTitle = (String)getGroup(groupPosition);

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.event_item, null);
        }

        TextView item = (TextView)convertView.findViewById(R.id.title);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(eventTitle);

        TextView item2 = (TextView)convertView.findViewById(R.id.date);
        item2.setText(eventsPostDates.get(groupPosition));
        item2.setTypeface(null, Typeface.BOLD);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        String eventDetails = (String)getChild(groupPosition, 0);

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.event_item_description, null);
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
        return true;
    }

}
