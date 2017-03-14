package com.shyky.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/19
 * @since 1.0
 */
public class SetOnItemClickListenerListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_simple);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            data.add("I am Text " + (j + 1));
        }
        TextView headerView = new TextView(this);
        headerView.setText("I am the header view.");
        headerView.setTextColor(Color.BLUE);
        headerView.setGravity(Gravity.CENTER);

        listView.addHeaderView(headerView);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, data));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = listView.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", data.get(position - headerViewsCount));
                }
                Toast.makeText(getApplicationContext(), "你点击了第 " + (position + 1) + "个item.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}