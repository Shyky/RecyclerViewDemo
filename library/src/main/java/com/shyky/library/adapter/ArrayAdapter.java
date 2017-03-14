package com.shyky.library.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 仿照ListView同名类{@link android.widget.ArrayAdapter}设计实现相同功能的RecyclerView Adapter
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/13
 * @since 1.0
 */
public class ArrayAdapter<T> extends ItemListenerAdapter implements Filterable {
    /**
     * Lock used to modify the content of {@link #objects}. Any write operation
     * performed on the array should be synchronized on this lock. This lock is also
     * used by the filter (see {@link #getFilter()} to make a synchronized copy of
     * the original array of data.
     */
    private final Object lock = new Object();
    private final LayoutInflater inflater;
    private final Context context;
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
    /**
     * If the inflated resource is not a TextView, {@code mFieldId} is used to find
     * a TextView inside the inflated views hierarchy. This field must contain the
     * identifier that matches the one defined in the resource file.
     */
    private int fieldId;
    /**
     * Indicates whether or not {@link #notifyDataSetChanged()} must be called whenever
     * {@link #objects} is modified.
     */
    private boolean notifyOnChange = true;
    // A copy of the original mObjects array, initialized from and then used instead as soon as
    // the mFilter ArrayFilter is used. mObjects will then only contain the filtered values.
    private ArrayList<T> originalValues;
    private ArrayFilter filter;

    protected class InternalViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public InternalViewHolder(View itemView) {
            super(itemView);
            try {
                if (fieldId == 0) {
                    //  If no custom field is assigned, assume the whole resource is a TextView
                    textView = (TextView) itemView;
                } else {
                    //  Otherwise, find the TextView field within the layout
                    textView = (TextView) itemView.findViewById(fieldId);

                    if (textView == null) {
                        throw new RuntimeException("Failed to find view with ID "
                                + context.getResources().getResourceName(fieldId)
                                + " in item layout");
                    }
                }
            } catch (ClassCastException e) {
                // Log.e("ArrayAdapter", "You must supply a resource ID for a TextView");
                throw new IllegalStateException(
                        "ArrayAdapter requires the resource ID to be a TextView", e);
            }
        }
    }

    /**
     * Constructor
     *
     * @param context The current context.
     */
    public ArrayAdapter(@NonNull Context context) {
        this(context, android.R.layout.simple_list_item_1, android.R.id.text1, new ArrayList<T>());
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param objects The objects to represent in the ListView.
     */
    public ArrayAdapter(@NonNull Context context, @NonNull T[] objects) {
        this(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
    }

    /**
     * Constructor
     *
     * @param context The current context.
     * @param objects The objects to represent in the ListView.
     */
    public ArrayAdapter(@NonNull Context context, @NonNull List<T> objects) {
        this(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     */
    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource) {
        this(context, resource, 0, new ArrayList<T>());
    }

    /**
     * Constructor
     *
     * @param context            The current context.
     * @param resource           The resource ID for a layout file containing a layout to use when
     *                           instantiating views.
     * @param textViewResourceId The id of the TextView within the layout resource to be populated
     */
    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        this(context, resource, textViewResourceId, new ArrayList<T>());
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull T[] objects) {
        this(context, resource, 0, Arrays.asList(objects));
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
    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull T[] objects) {
        this(context, resource, textViewResourceId, Arrays.asList(objects));
    }

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<T> objects) {
        this(context, resource, 0, objects);
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
    public ArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<T> objects) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.objects = objects;
        fieldId = textViewResourceId;
    }

    /**
     * Adds the specified object at the end of the array.
     *
     * @param object The object to add at the end of the array.
     */
    public void add(@Nullable T object) {
        synchronized (lock) {
            if (originalValues != null) {
                originalValues.add(object);
            } else {
                objects.add(object);
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    /**
     * Adds the specified Collection at the end of the array.
     *
     * @param collection The Collection to add at the end of the array.
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *                                       is not supported by this list
     * @throws ClassCastException            if the class of an element of the specified
     *                                       collection prevents it from being added to this list
     * @throws NullPointerException          if the specified collection contains one
     *                                       or more null elements and this list does not permit null
     *                                       elements, or if the specified collection is null
     * @throws IllegalArgumentException      if some property of an element of the
     *                                       specified collection prevents it from being added to this list
     */
    public void addAll(@NonNull Collection<? extends T> collection) {
        synchronized (lock) {
            if (originalValues != null) {
                originalValues.addAll(collection);
            } else {
                objects.addAll(collection);
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void addAll(T... items) {
        synchronized (lock) {
            if (originalValues != null) {
                Collections.addAll(originalValues, items);
            } else {
                Collections.addAll(objects, items);
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    /**
     * Inserts the specified object at the specified index in the array.
     *
     * @param object The object to insert into the array.
     * @param index  The index at which the object must be inserted.
     */
    public void insert(@Nullable T object, int index) {
        synchronized (lock) {
            if (originalValues != null) {
                originalValues.add(index, object);
            } else {
                objects.add(index, object);
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    /**
     * Removes the specified object from the array.
     *
     * @param object The object to remove.
     */
    public void remove(@Nullable T object) {
        synchronized (lock) {
            if (originalValues != null) {
                originalValues.remove(object);
            } else {
                objects.remove(object);
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    /**
     * Remove all elements from the list.
     */
    public void clear() {
        synchronized (lock) {
            if (originalValues != null) {
                originalValues.clear();
            } else {
                objects.clear();
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    /**
     * Sorts the content of this adapter using the specified comparator.
     *
     * @param comparator The comparator used to sort the objects contained
     *                   in this adapter.
     */
    public void sort(@NonNull Comparator<? super T> comparator) {
        synchronized (lock) {
            if (originalValues != null) {
                Collections.sort(originalValues, comparator);
            } else {
                Collections.sort(objects, comparator);
            }
        }
        if (notifyOnChange) notifyDataSetChanged();
    }

    public
    @Nullable
    T getItem(int position) {
        return objects.get(position);
    }

    /**
     * Returns the position of the specified item in the array.
     *
     * @param item The item to retrieve the position of.
     * @return The position of the specified item.
     */
    public int getPosition(@Nullable T item) {
        return objects.indexOf(item);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent, viewType);
        return new InternalViewHolder(inflater.inflate(resource, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        final InternalViewHolder viewHolder = (InternalViewHolder) holder;
        final T item = getItem(position);
        if (item == null)
            throw new NullPointerException("getItem(position)结果为null");
        if (item instanceof CharSequence) {
            viewHolder.textView.setText((CharSequence) item);
        } else {
            viewHolder.textView.setText(item.toString());
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public
    @NonNull
    Filter getFilter() {
        if (filter == null) {
            filter = new ArrayFilter();
        }
        return filter;
    }

    /**
     * <p>An array filter constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (originalValues == null) {
                synchronized (lock) {
                    originalValues = new ArrayList<>(objects);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<T> list;
                synchronized (lock) {
                    list = new ArrayList<>(originalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<T> values;
                synchronized (lock) {
                    values = new ArrayList<>(originalValues);
                }

                final int count = values.size();
                final ArrayList<T> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final T value = values.get(i);
                    final String valueText = value.toString().toLowerCase();

                    // First match against the whole, non-split value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
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
            objects = (List<T>) results.values;
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetChanged();
            }
        }
    }
}