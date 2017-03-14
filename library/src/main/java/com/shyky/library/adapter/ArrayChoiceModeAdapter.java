package com.shyky.library.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public class ArrayChoiceModeAdapter<T> extends ChoiceModeAdapter {
    /**
     * The resource indicating what views to inflate to display the content of this
     * array adapter.
     */
    private final int resource;
    /**
     * Contains the list of objects that represent the data of this ArrayAdapter.
     * The content of this list is referred to as "the array" in the documentation.
     */
    private List<T> objects;

    private class InternalViewHolder extends RecyclerView.ViewHolder {
        private CheckedTextView checkedTextView;

        public InternalViewHolder(View itemView) {
            super(itemView);
            checkedTextView = (CheckedTextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public ArrayChoiceModeAdapter(@NonNull Context context, @NonNull List<T> objects) {
        this(context, -1, objects);
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     * @param objects            The objects to represent in the ListView.
     */
    public ArrayChoiceModeAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> objects) {
        super(context);
        this.resource = resource;
        this.objects = objects;
//        fieldId = textViewResourceId;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (resource == -1)
            return new InternalViewHolder(inflateItemView(android.R.layout.simple_list_item_multiple_choice, parent));
        else
            return new InternalViewHolder(inflateItemView(resource, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final InternalViewHolder viewHolder = (InternalViewHolder) holder;
        final T item = getItem(position);
        if (item instanceof String) viewHolder.checkedTextView.setText((String) item);
        else
            viewHolder.checkedTextView.setText(item.toString());
    }

    @Nullable
    public T getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}