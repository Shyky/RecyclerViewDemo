package com.shyky.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.shyky.library.adapter.ChoiceModeAdapter;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public class SimpleChoiceModeAdapter extends ChoiceModeAdapter {
    public SimpleChoiceModeAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}