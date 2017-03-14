package com.shyky.demo.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shyky.demo.R;
import com.shyky.library.adapter.ArrayAdapter;
import com.shyky.library.intf.OnItemClickListener;
import com.shyky.library.view.widget.SmartRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/20
 * @since 1.0
 */
public class SingleChoiceSmartRecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_recycler_view_single_choice);
        final SmartRecyclerView recyclerView = (SmartRecyclerView) findViewById(R.id.recyclerView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            data.add("I am Text " + (j + 1));
        }

        recyclerView.setAdapter(new ArrayAdapter(this,
                android.R.layout.simple_list_item_single_choice, data));

        // 默认recyclerView.getCheckedItemPosition()为-1，没有选择任何item
        Log.d("Test", "onItemSelected position = " + recyclerView.getCheckedItemPosition());

        recyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, int position, long id) {
                final int headerViewsCount = recyclerView.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", data.get(position - headerViewsCount));
                }
                Toast.makeText(getApplicationContext(), "onItemClick position = " + (position + 1) + " , Current Checked item position = " + recyclerView.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}