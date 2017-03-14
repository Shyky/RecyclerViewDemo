package com.shyky.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView multiple choice
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public class MultipleChoiceListViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_multiple_choice);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            data.add("I am Text " + (j + 1));
        }

        // listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, data));

        Toast.makeText(getApplicationContext(), "默认选中项：" + print(listView.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = listView.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", data.get(position - headerViewsCount));
                }
                Toast.makeText(getApplicationContext(), "onItemClick position = " + (position + 1) + " , Checked item positions = " + print(listView.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String print(SparseBooleanArray booleanArray) {
        final int size = booleanArray == null ? 0 : booleanArray.size();
        if (size <= 0) {
            return "{}";
        }

        StringBuilder buffer = new StringBuilder(size * 28);
        buffer.append('{');
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            int key = booleanArray.keyAt(i);
            buffer.append(key);
        }
        buffer.append('}');
        return buffer.toString();
    }
}