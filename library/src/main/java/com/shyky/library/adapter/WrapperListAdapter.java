package com.shyky.library.adapter;

import android.widget.ListAdapter;
/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/16
 * @since 1.0
 */

/**
 * List adapter that wraps another list adapter. The wrapped adapter can be retrieved
 * by calling {@link #getWrappedAdapter()}.
 *
 * @see android.support.v7.widget.RecyclerView
 */
public interface WrapperListAdapter extends ListAdapter {
    /**
     * Returns the adapter wrapped by this list adapter.
     *
     * @return The {@link android.widget.ListAdapter} wrapped by this adapter.
     */
    ListAdapter getWrappedAdapter();
}