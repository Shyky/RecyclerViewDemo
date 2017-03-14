package com.shyky.library.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An easy adapter to map static data to views defined in an XML file. You can specify the data
 * backing the list as an ArrayList of Maps. Each entry in the ArrayList corresponds to one row
 * in the list. The Maps contain the data for each row. You also specify an XML file that
 * defines the views used to display the row, and a mapping from keys in the Map to specific
 * views.
 * <p>
 * Binding data to views occurs in two phases. First, if a
 * {@link android.widget.SimpleAdapter.ViewBinder} is available,
 * {@link android.widget.SimpleAdapter.ViewBinder#setViewValue(android.view.View, Object, String)}
 * is invoked. If the returned value is true, binding has occurred.
 * If the returned value is false, the following views are then tried in order:
 * <ul>
 * <li> A view that implements Checkable (e.g. CheckBox).  The expected bind value is a boolean.
 * <li> TextView.  The expected bind value is a string and {@link #setViewText(TextView, String)}
 * is invoked.
 * <li> ImageView. The expected bind value is a resource id or a string and
 * {@link #setViewImage(ImageView, int)} or {@link #setViewImage(ImageView, String)} is invoked.
 * </ul>
 * If no appropriate binding can be found, an {@link IllegalStateException} is thrown.
 */
public class SimpleAdapter extends RecyclerView.Adapter implements Filterable {
    private final LayoutInflater inflater;
    private int[] to;
    private String[] from;
    private ViewBinder viewBinder;
    private List<? extends Map<String, ?>> data;
    private int resource;
    private SimpleFilter filter;
    private ArrayList<Map<String, ?>> unfilteredData;

    protected class InternalViewHolder extends RecyclerView.ViewHolder {
        public InternalViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     *                 in the from parameter.
     */
    public SimpleAdapter(Context context, List<? extends Map<String, ?>> data, @LayoutRes int resource, String[] from, @IdRes int[] to) {
        this.data = data;
        this.resource = resource;
        this.from = from;
        this.to = to;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new InternalViewHolder(inflater.inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final InternalViewHolder viewHolder = (InternalViewHolder) holder;
        bindView(position, viewHolder.itemView);
    }

    /**
     * @see android.widget.Adapter#getItemId(int)
     */
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindView(int position, View view) {
        final Map dataSet = data.get(position);
        if (dataSet == null) {
            return;
        }

        final ViewBinder binder = viewBinder;
        final String[] from = this.from;
        final int[] to = this.to;
        final int count = to.length;

        for (int i = 0; i < count; i++) {
            final View v = view.findViewById(to[i]);
            if (v != null) {
                final Object data = dataSet.get(from[i]);
                String text = data == null ? "" : data.toString();
                if (text == null) {
                    text = "";
                }

                boolean bound = false;
                if (binder != null) {
                    bound = binder.setViewValue(v, data, text);
                }

                if (!bound) {
                    if (v instanceof Checkable) {
                        if (data instanceof Boolean) {
                            ((Checkable) v).setChecked((Boolean) data);
                        } else if (v instanceof TextView) {
                            // Note: keep the instanceof TextView check at the bottom of these
                            // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                            setViewText((TextView) v, text);
                        } else {
                            throw new IllegalStateException(v.getClass().getName() +
                                    " should be bound to a Boolean, not a " +
                                    (data == null ? "<unknown type>" : data.getClass()));
                        }
                    } else if (v instanceof TextView) {
                        // Note: keep the instanceof TextView check at the bottom of these
                        // ifs since a lot of views are TextViews (e.g. CheckBoxes).
                        setViewText((TextView) v, text);
                    } else if (v instanceof ImageView) {
                        if (data instanceof Integer) {
                            setViewImage((ImageView) v, (Integer) data);
                        } else {
                            setViewImage((ImageView) v, text);
                        }
                    } else {
                        throw new IllegalStateException(v.getClass().getName() + " is not a " +
                                " view that can be bounds by this SimpleAdapter");
                    }
                }
            }
        }
    }

    /**
     * Returns the {@link android.widget.SimpleAdapter.ViewBinder} used to bind data to views.
     *
     * @return a ViewBinder or null if the binder does not exist
     * @see #setViewBinder(ViewBinder)
     */
    public ViewBinder getViewBinder() {
        return viewBinder;
    }

    /**
     * Sets the binder used to bind data to views.
     *
     * @param viewBinder the binder used to bind data to views, can be null to
     *                   remove the existing binder
     * @see #getViewBinder()
     */
    public void setViewBinder(ViewBinder viewBinder) {
        this.viewBinder = viewBinder;
    }

    /**
     * Called by bindView() to set the image for an ImageView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to an ImageView.
     * <p>
     * This method is called instead of {@link #setViewImage(ImageView, String)}
     * if the supplied data is an int or Integer.
     *
     * @param v     ImageView to receive an image
     * @param value the value retrieved from the data set
     * @see #setViewImage(ImageView, String)
     */
    public void setViewImage(ImageView v, int value) {
        v.setImageResource(value);
    }

    /**
     * Called by bindView() to set the image for an ImageView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to an ImageView.
     * <p>
     * By default, the value will be treated as an image resource. If the
     * value cannot be used as an image resource, the value is used as an
     * image Uri.
     * <p>
     * This method is called instead of {@link #setViewImage(ImageView, int)}
     * if the supplied data is not an int or Integer.
     *
     * @param v     ImageView to receive an image
     * @param value the value retrieved from the data set
     * @see #setViewImage(ImageView, int)
     */
    public void setViewImage(ImageView v, String value) {
        try {
            v.setImageResource(Integer.parseInt(value));
        } catch (NumberFormatException nfe) {
            v.setImageURI(Uri.parse(value));
        }
    }

    /**
     * Called by bindView() to set the text for a TextView but only if
     * there is no existing ViewBinder or if the existing ViewBinder cannot
     * handle binding to a TextView.
     *
     * @param v    TextView to receive text
     * @param text the text to be set for the TextView
     */
    public void setViewText(TextView v, String text) {
        v.setText(text);
    }

    public Filter getFilter() {
        if (filter == null) {
            filter = new SimpleFilter();
        }
        return filter;
    }

    /**
     * This class can be used by external clients of SimpleAdapter to bind
     * values to views.
     * <p>
     * You should use this class to bind values to views that are not
     * directly supported by SimpleAdapter or to change the way binding
     * occurs for views supported by SimpleAdapter.
     *
     * @see android.widget.SimpleAdapter#setViewImage(ImageView, int)
     * @see android.widget.SimpleAdapter#setViewImage(ImageView, String)
     * @see android.widget.SimpleAdapter#setViewText(TextView, String)
     */
    public interface ViewBinder {
        /**
         * Binds the specified data to the specified view.
         * <p>
         * When binding is handled by this ViewBinder, this method must return true.
         * If this method returns false, SimpleAdapter will attempts to handle
         * the binding on its own.
         *
         * @param view               the view to bind the data to
         * @param data               the data to bind to the view
         * @param textRepresentation a safe String representation of the supplied data:
         *                           it is either the result of data.toString() or an empty String but it
         *                           is never null
         * @return true if the data was bound to the view, false otherwise
         */
        boolean setViewValue(View view, Object data, String textRepresentation);
    }

    /**
     * <p>An array filters constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class SimpleFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (unfilteredData == null) {
                unfilteredData = new ArrayList<Map<String, ?>>(data);
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<Map<String, ?>> list = unfilteredData;
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<Map<String, ?>> unfilteredValues = unfilteredData;
                int count = unfilteredValues.size();

                ArrayList<Map<String, ?>> newValues = new ArrayList<Map<String, ?>>(count);

                for (int i = 0; i < count; i++) {
                    Map<String, ?> h = unfilteredValues.get(i);
                    if (h != null) {

                        int len = to.length;

                        for (int j = 0; j < len; j++) {
                            String str = (String) h.get(from[j]);

                            String[] words = str.split(" ");
                            int wordCount = words.length;

                            for (int k = 0; k < wordCount; k++) {
                                String word = words[k];

                                if (word.toLowerCase().startsWith(prefixString)) {
                                    newValues.add(h);
                                    break;
                                }
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // noinspection unchecked
            data = (List<Map<String, ?>>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetChanged();
//                notifyDataSetInvalidated();
            }
        }
    }
}