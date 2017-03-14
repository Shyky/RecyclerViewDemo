package com.shyky.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/13
 * @since 1.0
 */
public class StaggeredGridLayoutManagerRecyclerViewActivity extends AppCompatActivity {
    private class ImageAdapter extends RecyclerView.Adapter {
        private Context context;
        private List<String> data;

        public ImageAdapter(Context context, List<String> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ImageViewHolder viewHolder = (ImageViewHolder) holder;
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (position % 2 == 0) {
                params.height = 300;
            }
            else {
                params.height = 250;
            }
            viewHolder.view.setLayoutParams(params);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private class ImageViewHolder extends RecyclerView.ViewHolder {
            public final View view;

            public ImageViewHolder(View itemView) {
                super(itemView);
                view = itemView.findViewById(R.id.view);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<String> data = new ArrayList<>();
        for (int j = 0; j < 20; j++) {
            data.add("I am Text " + (j + 1));
        }
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setAdapter(new ArrayAdapter(this, data));
        recyclerView.setAdapter(new ImageAdapter(this, data));
    }
}