package com.shyky.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/2/8
 * @since 1.0
 */
public class SimpleExpandableListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_listview);

        ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        final List<String> groups = new ArrayList<>();
        groups.add("China");
        groups.add("America");
        expandableListView.setAdapter(new BaseExpandableListAdapter() {
            @Override
            public int getGroupCount() {
                return groups.size();
            }

            @Override
            public int getChildrenCount(int groupPosition) {
                return 2;
            }

            @Override
            public Object getGroup(int groupPosition) {
                return groups.get(groupPosition);
            }

            @Override
            public Object getChild(int groupPosition, int childPosition) {
                return "Child";
            }

            @Override
            public long getGroupId(int groupPosition) {
                return 0;
            }

            @Override
            public long getChildId(int groupPosition, int childPosition) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(getGroup(groupPosition).toString());
                return textView;
            }

            @Override
            public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
                TextView textView = new TextView(getApplicationContext());
                textView.setText(getChild(groupPosition, childPosition).toString());
                return textView;
            }

            @Override
            public boolean isChildSelectable(int groupPosition, int childPosition) {
                return false;
            }
        });
    }
}