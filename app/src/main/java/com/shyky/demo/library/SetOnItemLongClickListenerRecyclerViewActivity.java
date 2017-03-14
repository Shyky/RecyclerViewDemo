package com.shyky.demo.library;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shyky.demo.R;
import com.shyky.library.adapter.ArrayAdapter;
import com.shyky.library.intf.OnItemLongClickListener;
import com.shyky.library.view.widget.SmartRecyclerView;

import java.util.ArrayList;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/19
 * @since 1.0
 */
public class SetOnItemLongClickListenerRecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_recycler_view_simple);
        SmartRecyclerView recyclerView = (SmartRecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> data = new ArrayList<>();

        for (int j = 0; j < 20; j++) {
            data.add("I am Text " + (j + 1));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        TextView headerView = new TextView(this);
//        headerView.setText("I am the header view.");
//        headerView.setTextColor(Color.BLUE);
//        headerView.setGravity(Gravity.CENTER);
//
//        recyclerView.addHeaderView(headerView);
        recyclerView.setAdapter(new ArrayAdapter(this, data));

        recyclerView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, int position, long id) {
                Log.d("Test", "onItemLongClick position = " + position);
                Toast.makeText(getApplicationContext(), "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}