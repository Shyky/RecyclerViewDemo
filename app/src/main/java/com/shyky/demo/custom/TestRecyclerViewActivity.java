package com.shyky.demo.custom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shyky.demo.R;

import java.util.ArrayList;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/13
 * @since 1.0
 */
public class TestRecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> data = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            data.add("I am Text " + j/*+ (j + 1)*/);
        }
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new TestAdapter(this, data));
    }
}