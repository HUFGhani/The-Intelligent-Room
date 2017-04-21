package com.project.smarthome.smarthome.Views.RecyclerViews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.project.smarthome.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NavigationDrawerExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listHeadings;
    private HashMap<String, List<String>> listDetail;

    public NavigationDrawerExpandableListAdapter(Context context, HashMap<String, List<String>> listDetail) {
        this.context = context;
        this.listHeadings = new ArrayList<>(listDetail.keySet());
        this.listDetail = listDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return listDetail
                .get(this.listHeadings.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View view, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.expandable_list_view_item, null);
        }
        TextView expandedListTextView = (TextView) view.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);
        return view;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return listDetail.get(listHeadings.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return listHeadings.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return listHeadings.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View view, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.expandable_list_view_group, null);
        }
        TextView listTitleTextView = (TextView) view.findViewById(R.id.listTitle);
        listTitleTextView.setText(listTitle);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

}
