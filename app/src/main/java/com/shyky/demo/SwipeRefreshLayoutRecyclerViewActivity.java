package com.shyky.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shyky.library.adapter.ArrayAdapter;

import java.util.ArrayList;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/13
 * @since 1.0
 */
public class SwipeRefreshLayoutRecyclerViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<String> data;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        data = new ArrayList<>();
        adapter = new ArrayAdapter(this, data);
        swipeRefreshLayout.setOnRefreshListener(this);

        for (int j = 0; j < 20; j++) {
            data.add("I am Text " + (j + 1));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        final int start = data.size();
        // 3秒后加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int j = start; j < start + 10; j++) {
                    data.add("I the new Text " + (j + 1));
                }
                adapter.notifyDataSetChanged();
                // 记得要设置为false，表示刷新完成，此时界面上就不会出现转圈的图标
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }
}