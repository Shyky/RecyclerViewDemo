package com.shyky.library.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 对原有的{@link android.support.v7.widget.RecyclerView.Adapter}进行包装加入Header和Footer
 *
 * @author Shyky
 * @version 1.2
 * @date 2017/1/12
 * @since 1.0
 */
public class HeaderFooterViewAdapter extends ItemListenerAdapter {
    private static final int TYPE_HEADER = 11;
    private static final int TYPE_FOOTER = 12;
    private static final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<>();
    private final RecyclerView.Adapter adapter;
    // These two ArrayList are assumed to NOT be null.
    // They are indeed created when declared in ListView and then shared.
    private final List<View> headerViews;
    private final List<View> footerViews;
    private View headerView;
    private View footerView;

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    public HeaderFooterViewAdapter(@NonNull Context context, @NonNull RecyclerView.Adapter adapter) {
        this.adapter = adapter;
        headerViews = EMPTY_INFO_LIST;
        footerViews = EMPTY_INFO_LIST;
    }

    public HeaderFooterViewAdapter(@NonNull Context context, @NonNull List<View> headerViews, @NonNull List<View> footerViews, @NonNull RecyclerView.Adapter adapter) {
        this.adapter = adapter;

        if (headerViews == null) {
            this.headerViews = EMPTY_INFO_LIST;
        } else {
            this.headerViews = headerViews;
        }

        if (footerViews == null) {
            this.footerViews = EMPTY_INFO_LIST;
        } else {
            this.footerViews = footerViews;
        }
    }

    public int getHeadersCount() {
        return headerViews.size();
    }

    public int getFootersCount() {
        return footerViews.size();
    }

    public boolean isEmpty() {
        return adapter == null || adapter.getItemCount() == 0;
    }

    public boolean removeHeader(View v) {
        for (int i = 0; i < headerViews.size(); i++) {
            View view = headerViews.get(i);
            if (view == v) {
                headerViews.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean removeFooter(View v) {
        for (int i = 0; i < footerViews.size(); i++) {
            View view = footerViews.get(i);
            if (view == v) {
                footerViews.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (adapter != null) {
            return getHeadersCount() + adapter.getItemCount() + getFootersCount();
        } else {
            return getHeadersCount() + getFootersCount();
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Header
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return TYPE_HEADER;
        }

        // Adapter
        final int adjPosition = position - numHeaders;
        if (adapter != null) {
            final int adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                return super.getItemViewType(position);
            }
        }

        // Footer
        return TYPE_FOOTER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(headerView);
            case TYPE_FOOTER:
                return new FooterViewHolder(footerView);
            default:
                if (adapter != null)
                    return adapter.onCreateViewHolder(parent, viewType);
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        // Header (negative positions will throw an IndexOutOfBoundsException)
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            headerView = headerViews.get(position);
        }

        // Adapter
        final int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (adapter != null) {
            adapterCount = adapter.getItemCount();
            if (adjPosition < adapterCount) {
                adapter.onBindViewHolder(holder, adjPosition);
            }
        }

        // Footer (off-limits positions will throw an IndexOutOfBoundsException)
        footerView = footerViews.get(adjPosition - adapterCount);
    }

    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(observer);
        }
    }

    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(observer);
        }
    }

    public RecyclerView.Adapter getWrappedAdapter() {
        return adapter;
    }
}