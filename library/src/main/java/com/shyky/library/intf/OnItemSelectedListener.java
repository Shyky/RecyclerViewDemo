package com.shyky.library.intf;

import android.view.View;
import android.view.ViewGroup;
/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/19
 * @since 1.0
 */

/**
 * Interface definition for a callback to be invoked when
 * an item in this view has been selected.
 */
public interface OnItemSelectedListener {
    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    void onItemSelected(ViewGroup parent, View view, int position, long id);

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    void onNothingSelected(ViewGroup parent);
}