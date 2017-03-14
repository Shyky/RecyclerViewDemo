package com.shyky.library.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.shyky.library.R;
import com.shyky.library.adapter.ArrayAdapter;
import com.shyky.library.adapter.HeaderFooterViewAdapter;
import com.shyky.library.adapter.ItemListenerAdapter;
import com.shyky.library.intf.OnItemClickListener;
import com.shyky.library.intf.OnItemLongClickListener;
import com.shyky.library.intf.OnItemSelectedListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/12
 * @since 1.0
 */
public class SmartRecyclerView extends RecyclerView {
    /**
     * Represents an invalid position. All valid positions are in the range 0 to 1 less than the
     * number of items in the current adapter.
     */
    public static final int INVALID_POSITION = -1;
    /**
     * Normal list that does not indicate choices
     */
    public static final int CHOICE_MODE_NONE = 0;

    /**
     * The list allows up to one choice
     */
    public static final int CHOICE_MODE_SINGLE = 1;

    /**
     * The list allows multiple choices
     */
    public static final int CHOICE_MODE_MULTIPLE = 2;

    /**
     * The list allows multiple choices in a modal selection mode
     */
    public static final int CHOICE_MODE_MULTIPLE_MODAL = 3;
    private static final int[] CLIP_TO_PADDING_ATTR = {android.R.attr.clipToPadding};
    /**
     * The adapter containing the data to be displayed by this view
     */
    private Adapter internalAdapter;
    /**
     * Should be used by subclasses to listen to changes in the data set
     */
    private AdapterDataObserver adapterDataObserver;
    private List<View> headerViews;
    private List<View> footerViews;
    /**
     * View to show if there are no items to show.
     */
    private View emptyView;
    /**
     * The listener that receives notifications when an item is clicked.
     */
    private OnItemClickListener onItemClickListener;
    /**
     * The listener that receives notifications when an item is long clicked.
     */
    private OnItemLongClickListener onItemLongClickListener;
    /**
     * The listener that receives notifications when an item is selected.
     */
    private OnItemSelectedListener onItemSelectedListener;
    /**
     * Controls if/how the user may choose/check items in the list
     */
    private int mChoiceMode = CHOICE_MODE_NONE;
    /**
     * Running state of which positions are currently checked
     */
    private SparseBooleanArray checkStates;

    private class AdapterDataObserver extends RecyclerView.AdapterDataObserver {
        /**
         * RecyclerView内部的观察者对象
         */
        private RecyclerView.AdapterDataObserver internalAdapterDataObserver;

        private Field getDeclaredField(Class clazz, String fieldName) {
            Field field;
            for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field;
                } catch (Exception e) {
//                    Log.e(TAG, e.toString());
                }
            }
            return null;
        }

        public AdapterDataObserver() {
            try {
                // 通过反射获取父类观察者对象
                Field declaredField = getDeclaredField(RecyclerView.class, "mObserver");
                if (declaredField != null) {
                    internalAdapterDataObserver = (RecyclerView.AdapterDataObserver) declaredField.get(SmartRecyclerView.this);
                }
            } catch (Exception e) {
//                Log.e(TAG, e.toString());
            }
        }

        @Override
        public void onChanged() {
            if (internalAdapterDataObserver != null) {
                internalAdapterDataObserver.onChanged();
            }
            final Adapter adapter = getAdapter();
            final boolean empty = ((adapter == null) || adapter.getItemCount() == 0);
            if (emptyView != null) {
                if (empty) {
                    emptyView.setVisibility(VISIBLE);
                    setVisibility(GONE);
                } else {
                    emptyView.setVisibility(VISIBLE);
                    setVisibility(GONE);
                }
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            onChanged();
            if (internalAdapterDataObserver != null) {
                internalAdapterDataObserver.onItemRangeChanged(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            onChanged();
            if (internalAdapterDataObserver != null) {
                internalAdapterDataObserver.onItemRangeChanged(positionStart, itemCount, payload);
            }
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            onChanged();
            if (internalAdapterDataObserver != null) {
                internalAdapterDataObserver.onItemRangeInserted(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            onChanged();
            if (internalAdapterDataObserver != null) {
                internalAdapterDataObserver.onItemRangeRemoved(positionStart, itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            onChanged();
            if (internalAdapterDataObserver != null) {
                internalAdapterDataObserver.onItemRangeMoved(fromPosition, toPosition, itemCount);
            }
        }
    }

    public SmartRecyclerView(Context context) {
        this(context, null);
    }

    public SmartRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, CLIP_TO_PADDING_ATTR, defStyle, 0);
//            mClipToPadding = a.getBoolean(0, true);
            a.recycle();
        }

        if (attrs != null) {
            int defStyleRes = 0;
            final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SmartRecyclerView);
//            int descendantFocusability = typedArray.getInt(
//                    android.support.v7.recyclerview.R.styleable.RecyclerView_android_descendantFocusability, -1);
//            if (descendantFocusability == -1) {
//                setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
//            }

            CharSequence[] entries = typedArray.getTextArray(R.styleable.SmartRecyclerView_android_entries);
            if (entries == null)
                entries = typedArray.getTextArray(R.styleable.SmartRecyclerView_app_entries);
            if (entries != null) {
                setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, entries));
            }

            typedArray.recycle();
//            createLayoutManager(context, layoutManagerName, attrs, defStyle, defStyleRes);

//            if (Build.VERSION.SDK_INT >= 21) {
//                a = context.obtainStyledAttributes(attrs, NESTED_SCROLLING_ATTRS,
//                        defStyle, defStyleRes);
//                nestedScrollingEnabled = a.getBoolean(0, true);
//                a.recycle();
//            }
        }

        init();
    }

    private void init() {
        headerViews = new ArrayList<>();
        footerViews = new ArrayList<>();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (internalAdapter != null && adapterDataObserver == null) {
            adapterDataObserver = new AdapterDataObserver();
            internalAdapter.registerAdapterDataObserver(adapterDataObserver);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter != null && adapterDataObserver != null) {
            internalAdapter.unregisterAdapterDataObserver(adapterDataObserver);
        }
        if (hasHeaderView() || hasFooterView()) {
            internalAdapter = wrapHeaderListAdapterInternal(headerViews, footerViews, adapter);
        } else {
            internalAdapter = adapter;
        }
        if (adapter != null) {
            adapterDataObserver = new AdapterDataObserver();
            internalAdapter.registerAdapterDataObserver(adapterDataObserver);
        }
        adapterDataObserver.onChanged();
        super.setAdapter(adapter);
    }

    public boolean hasHeaderView() {
        return getHeaderViewsCount() > 0;
    }

    public int getHeaderViewsCount() {
        return headerViews.size();
    }

    /**
     * 添加Header
     *
     * @param resId 布局文件资源ID
     */
    public void addHeaderView(@LayoutRes int resId) {
        addHeaderView(View.inflate(getContext(), resId, (ViewGroup) getParent()));
    }

    /**
     * Add a fixed view to appear at the top of the list. If addHeaderView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * <p>
     * Note: When first introduced, this method could only be called before
     * setting the adapter with {@link #setAdapter(android.support.v7.widget.RecyclerView.Adapter)}. Starting with
     * {@link android.os.Build.VERSION_CODES#KITKAT}, this method may be
     * called at any time. If the ListView's adapter does not extend
     * {@link HeaderFooterViewAdapter}, it will be wrapped with a supporting
     * instance of {@link com.shyky.library.adapter.WrapperListAdapter}.
     *
     * @param view The view to add.
     */
    public void addHeaderView(@NonNull View view) {
        headerViews.add(view);

        // Wrap the adapter if it wasn't already wrapped.
        if (internalAdapter != null) {
            if (!(internalAdapter instanceof HeaderFooterViewAdapter)) {
                wrapHeaderListAdapterInternal();
            }

            // In the case of re-adding a header view, or adding one later on,
            // we need to notify the observer.
            if (adapterDataObserver != null) {
                adapterDataObserver.onChanged();
            }
        }
    }

    public boolean hasFooterView() {
        return getFooterViewsCount() > 0;
    }

    public int getFooterViewsCount() {
        return footerViews.size();
    }

    /**
     * Add a fixed view to appear at the bottom of the list. If addFooterView is
     * called more than once, the views will appear in the order they were
     * added. Views added using this call can take focus if they want.
     * <p>
     * Note: When first introduced, this method could only be called before
     * setting the adapter with {@link #setAdapter(android.support.v7.widget.RecyclerView.Adapter)}. Starting with
     * {@link android.os.Build.VERSION_CODES#KITKAT}, this method may be
     * called at any time. If the ListView's adapter does not extend
     * {@link HeaderFooterViewAdapter}, it will be wrapped with a supporting
     * instance of {@link com.shyky.library.adapter.WrapperListAdapter}.
     *
     * @param view The view to add.
     */
    public void addFooterView(@NonNull View view) {
        footerViews.add(view);

        // Wrap the adapter if it wasn't already wrapped.
        if (internalAdapter != null) {
            if (!(internalAdapter instanceof HeaderFooterViewAdapter)) {
                wrapHeaderListAdapterInternal();
            }

            // In the case of re-adding a footer view, or adding one later on,
            // we need to notify the observer.
            if (adapterDataObserver != null) {
                adapterDataObserver.onChanged();
            }
        }
    }

    protected HeaderFooterViewAdapter wrapHeaderListAdapterInternal(
            List<View> headerViews,
            List<View> footerViews,
            Adapter adapter) {
        return new HeaderFooterViewAdapter(getContext(), headerViews, footerViews, adapter);
    }

    protected void wrapHeaderListAdapterInternal() {
        internalAdapter = wrapHeaderListAdapterInternal(headerViews, footerViews, internalAdapter);
    }

    /**
     * Sets the view to show if the adapter is empty
     */
    public void setEmptyView(@LayoutRes int resId) {
        setEmptyView(View.inflate(getContext(), resId, null));
    }

    /**
     * Sets the view to show if the adapter is empty
     *
     * @param emptyView the view to show
     */
    public void setEmptyView(@NonNull View emptyView) {
        this.emptyView = emptyView;
        ((ViewGroup) getRootView()).addView(emptyView);
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked.
     *
     * @param listener The callback that will be invoked.
     */
    public void setOnItemClickListener(@Nullable @NonNull OnItemClickListener listener) {
        onItemClickListener = listener;
//        Log.d(TAG, "setOnItemClickListener...");
//        if (internalAdapter instanceof HeaderFooterViewAdapter) {
        ((ItemListenerAdapter) internalAdapter).setOnItemClickListener(onItemClickListener);
//        }
    }

    /**
     * @return The callback to be invoked with an item in this AdapterView has
     * been clicked, or null id no callback has been set.
     */
    @Nullable
    public final OnItemClickListener getOnItemClickListener() {
        if (internalAdapter instanceof HeaderFooterViewAdapter) {
            return ((HeaderFooterViewAdapter) internalAdapter).getOnItemClickListener();
        }
        return null;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been clicked and held
     *
     * @param listener The callback that will run
     */
    public void setOnItemLongClickListener(@Nullable @NonNull OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
        ((ItemListenerAdapter) internalAdapter).setOnItemLongClickListener(onItemLongClickListener);
    }

    /**
     * @return The callback to be invoked with an item in this AdapterView has
     * been clicked and held, or null id no callback as been set.
     */
    public final OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    /**
     * Register a callback to be invoked when an item in this AdapterView has
     * been selected.
     *
     * @param listener The callback that will run
     */
    public void setOnItemSelectedListener(@Nullable @NonNull OnItemSelectedListener listener) {
        onItemSelectedListener = listener;
    }

    @Nullable
    public final OnItemSelectedListener getOnItemSelectedListener() {
        return onItemSelectedListener;
    }

    /**
     * Returns the currently checked item. The result is only valid if the choice
     * mode has been set to {@link #CHOICE_MODE_SINGLE}.
     *
     * @return The position of the currently checked item or
     * {@link #INVALID_POSITION} if nothing is selected
     * @see #setChoiceMode(int)
     */
    public int getCheckedItemPosition() {
        if (mChoiceMode == CHOICE_MODE_SINGLE && checkStates != null && checkStates.size() == 1) {
            return checkStates.keyAt(0);
        }
        return INVALID_POSITION;
    }
}