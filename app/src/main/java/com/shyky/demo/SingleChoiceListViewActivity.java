package com.shyky.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView single choice
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/20
 * @since 1.0
 */
public class SingleChoiceListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_single_choice);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            data.add("I am Text " + (j + 1));
        }

         listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, data));

        // 默认listView.getCheckedItemPosition()为-1，没有选择任何item
        Log.d("Test", "onItemSelected position = " + listView.getCheckedItemPosition());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = listView.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", data.get(position - headerViewsCount));
                }
                Toast.makeText(getApplicationContext(), "onItemClick position = " + (position + 1) + " , Current Checked item position = " + listView.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}