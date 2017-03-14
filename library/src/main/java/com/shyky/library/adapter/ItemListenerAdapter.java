package com.shyky.library.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.shyky.library.intf.OnItemClickListener;
import com.shyky.library.intf.OnItemLongClickListener;

/**
 * 对原有的{@link android.support.v7.widget.RecyclerView.Adapter}处理了item点击、长按事件
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/16
 * @since 1.0
 */
public abstract class ItemListenerAdapter extends RecyclerView.Adapter {
    /**
     * The listener that receives notifications when an item is clicked.
     */
    private OnItemClickListener onItemClickListener;
    /**
     * The listener that receives notifications when an item is long clicked.
     */
    private OnItemLongClickListener onItemLongClickListener;
    private ViewGroup parent;

    @CallSuper
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        return null;
    }

    @CallSuper
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("xxx", "onClick... v = " + v);
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(parent, v, holder.getAdapterPosition(), getItemId(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null)
                    onItemLongClickListener.onItemLongClick(parent, v, holder.getAdapterPosition(), getItemId(holder.getAdapterPosition()));
                return false;
            }
        });
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable @NonNull OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an item in this AdapterView has
     * been clicked, or null id no callback has been set.
     */
    @Nullable
    public final OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(@Nullable @NonNull OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }

    /**
     * @return The callback to be invoked with an item in this AdapterView has
     * been clicked and held, or null id no callback as been set.
     */
    public final OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }
}