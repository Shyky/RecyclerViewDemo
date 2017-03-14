package com.shyky.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/24
 * @since 1.0
 */
public class DividerItemDecorationRecyclerViewActivity extends AppCompatActivity {
    private class SimpleAdapter extends RecyclerView.Adapter {
        private final LayoutInflater layoutInflater;
        private List<String> data;

        private class SimpleViewHolder extends RecyclerView.ViewHolder {
            public final TextView textView;

            public SimpleViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(android.R.id.title);
            }
        }

        public SimpleAdapter(Context context, List<String> data) {
            layoutInflater = LayoutInflater.from(context);
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SimpleViewHolder(layoutInflater.inflate(R.layout.item_text, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleViewHolder viewHolder = (SimpleViewHolder) holder;
            viewHolder.textView.setText(getItem(position));
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public String getItem(int position) {
            return data.get(position);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        setTitle("DividerRecyclerViewActivity");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // You must call this method or nothing display
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        List<String> data = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            data.add("I am Text" + (j + 1));
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }
}