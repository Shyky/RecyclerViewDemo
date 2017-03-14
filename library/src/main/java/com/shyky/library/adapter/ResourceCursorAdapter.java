package com.shyky.library.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/16
 * @since 1.0
 */

/**
 * An easy adapter that creates views defined in an XML file. You can specify
 * the XML file that defines the appearance of the views.
 */
public abstract class ResourceCursorAdapter extends CursorAdapter {
    private int mLayout;
    private int mDropDownLayout;
    private LayoutInflater mInflater;
    private LayoutInflater mDropDownInflater;

    /**
     * Constructor the enables auto-requery.
     *
     * @param context The context where the ListView associated with this adapter is running
     * @param layout  resource identifier of a layout file that defines the views
     *                for this list item.  Unless you override them later, this will
     *                define both the item views and the drop down views.
     * @deprecated This option is discouraged, as it results in Cursor queries
     * being performed on the application's UI thread and thus can cause poor
     * responsiveness or even Application Not Responding errors.  As an alternative,
     * use {@link android.app.LoaderManager} with a {@link android.content.CursorLoader}.
     */
    @Deprecated
    public ResourceCursorAdapter(Context context, int layout, Cursor c) {
        super(context, c);
        mLayout = mDropDownLayout = layout;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDropDownInflater = mInflater;
    }

    /**
     * Constructor with default behavior as per
     * {@link android.widget.CursorAdapter#CursorAdapter(Context, Cursor, boolean)}; it is recommended
     * you not use this, but instead {@link #ResourceCursorAdapter(Context, int, Cursor, int)}.
     * When using this constructor, {@link #FLAG_REGISTER_CONTENT_OBSERVER}
     * will always be set.
     *
     * @param context     The context where the ListView associated with this adapter is running
     * @param layout      resource identifier of a layout file that defines the views
     *                    for this list item.  Unless you override them later, this will
     *                    define both the item views and the drop down views.
     * @param c           The cursor from which to get the data.
     * @param autoRequery If true the adapter will call requery() on the
     *                    cursor whenever it changes so the most recent
     *                    data is always displayed.  Using true here is discouraged.
     */
    public ResourceCursorAdapter(Context context, int layout, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mLayout = mDropDownLayout = layout;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDropDownInflater = mInflater;
    }

    /**
     * Standard constructor.
     *
     * @param context The context where the ListView associated with this adapter is running
     * @param layout  Resource identifier of a layout file that defines the views
     *                for this list item.  Unless you override them later, this will
     *                define both the item views and the drop down views.
     * @param c       The cursor from which to get the data.
     * @param flags   Flags used to determine the behavior of the adapter,
     *                as per {@link android.widget.CursorAdapter#CursorAdapter(Context, Cursor, int)}.
     */
    public ResourceCursorAdapter(Context context, int layout, Cursor c, int flags) {
        super(context, c, flags);
        mLayout = mDropDownLayout = layout;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDropDownInflater = mInflater;
    }

    /**
     * Inflates view(s) from the specified XML file.
     *
     * @see android.widget.CursorAdapter#newView(android.content.Context,
     * android.database.Cursor, ViewGroup)
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(mLayout, parent, false);
    }

    @Override
    public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
        return mDropDownInflater.inflate(mDropDownLayout, parent, false);
    }

    /**
     * <p>Sets the layout resource of the item views.</p>
     *
     * @param layout the layout resources used to create item views
     */
    public void setViewResource(int layout) {
        mLayout = layout;
    }

    /**
     * <p>Sets the layout resource of the drop down views.</p>
     *
     * @param dropDownLayout the layout resources used to create drop down views
     */
    public void setDropDownViewResource(int dropDownLayout) {
        mDropDownLayout = dropDownLayout;
    }
}