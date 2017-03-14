package com.shyky.library.intf;

import android.view.View;
import android.view.ViewGroup;
/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/16
 * @since 1.0
 */

/**
 * Interface definition for a callback to be invoked when an item in this
 * view has been clicked and held.
 */
public interface OnItemLongClickListener {
    /**
     * Callback method to be invoked when an item in this view has been
     * clicked and held.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access
     * the data associated with the selected item.
     *
     * @param parent   The AbsListView where the click happened
     * @param view     The view within the AbsListView that was clicked
     * @param position The position of the view in the list
     * @param id       The row id of the item that was clicked
     * @return true if the callback consumed the long click, false otherwise
     */
    boolean onItemLongClick(ViewGroup parent, View view, int position, long id);
}