package com.shyky.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView添加多个header和footer
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/3/2
 * @since 1.0
 */
public final class MultipleHeaderFooterRecyclerViewActivity extends AppCompatActivity {
    private static final String TAG = "MultipleHeaderFooter";
    private RecyclerView recyclerView;

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout container;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView;
        }
    }

    private static class FooterViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout container;

        public FooterViewHolder(View itemView) {
            super(itemView);
            container = (LinearLayout) itemView;
        }
    }

    private static class NormalViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public NormalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.title);
        }
    }

    private class MultipleHeaderFooterAdapter extends RecyclerView.Adapter {
        private static final int TYPE_HEADER = 11;
        private static final int TYPE_FOOTER = 12;
        private final Context context;
        private final LayoutInflater layoutInflater;
        private final ArrayList<View> headerViews;
        private final ArrayList<View> footerViews;
        private final List<String> data;

        public MultipleHeaderFooterAdapter(Context context, List<String> data) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            headerViews = new ArrayList<>();
            footerViews = new ArrayList<>();
            this.data = data;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < getHeaderCount()) {
                return TYPE_HEADER;
            }
            if (hasFooter() && position >= getHeaderCount() + getDataCount()) {
                return TYPE_FOOTER;
            }
            return super.getItemViewType(position);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case TYPE_HEADER:
                    final LinearLayout headerContainer = new LinearLayout(context);
                    return new HeaderViewHolder(headerContainer);
                case TYPE_FOOTER:
                    final LinearLayout footerContainer = new LinearLayout(context);
                    return new FooterViewHolder(footerContainer);
                default:
                    return new NormalViewHolder(layoutInflater.inflate(R.layout.item_text, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d(TAG, "getItemCount = " + getItemCount());
            Log.d(TAG, "position = " + position);
            final int viewType = getItemViewType(position);
            Log.d(TAG, "viewType = " + viewType);
            switch (viewType) {
                case TYPE_HEADER:
                    final HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                    headerViewHolder.container.addView(getHeader(position));
                    break;
                case TYPE_FOOTER:
                    final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                    footerViewHolder.container.addView(getFooter(position));
                    break;
                default:
                    String item = getItem(position);
                    final NormalViewHolder viewHolder = (NormalViewHolder) holder;
                    viewHolder.textView.setText(item);
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return getHeaderCount() + data.size() + getFooterCount();
        }

        public String getItem(int position) {
            final int itemPosition = position - getHeaderCount();
            return data.get(itemPosition);
        }

        public int getDataCount() {
            return data == null || data.isEmpty() ? 0 : data.size();
        }

        public int getHeaderCount() {
            return headerViews.size();
        }

        public boolean hasHeader() {
            return getHeaderCount() != 0;
        }

        public void addHeader(View view) {
            headerViews.add(view);
        }

        public View getHeader(int index) {
            return headerViews.get(index);
        }

        public int getFooterCount() {
            return headerViews.size();
        }

        public boolean hasFooter() {
            return getFooterCount() != 0;
        }

        public void addFooter(View view) {
            footerViews.add(view);
        }

        public View getFooter(int index) {
            final int footerIndex = index - (getHeaderCount() + getDataCount());
            return footerViews.get(footerIndex);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // 不要忘了设置布局管理器，否则没有数据显示
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            data.add("I am text " + (j + 1));
        }
        MultipleHeaderFooterAdapter adapter = new MultipleHeaderFooterAdapter(this, data);

        // 添加多个header和footer
        TextView header1 = new TextView(this);
        header1.setTextColor(Color.RED);
        header1.setText("I am header 1.");
        TextView header2 = new TextView(this);
        header2.setTextColor(Color.BLUE);
        header2.setText("I am header 2.");
        TextView header3 = new TextView(this);
        header3.setTextColor(Color.GREEN);
        header3.setText("I am header 3.");
        // 需要在setAdapter方法之前添加或者在其之后但是需要调用adapter.notifyDataSetChanged()方法刷新
        adapter.addHeader(header1);
        adapter.addHeader(header2);
        adapter.addHeader(header3);

//        // 添加多个footer
//        TextView footer1 = new TextView(this);
//        footer1.setTextColor(Color.GRAY);
//        footer1.setText("I am footer 1.");
//        TextView footer2 = new TextView(this);
//        footer2.setTextColor(Color.GRAY);
//        footer2.setText("I am footer 2.");
//        TextView footer3 = new TextView(this);
//        footer3.setTextColor(Color.GRAY);
//        footer3.setText("I am footer 3.");
//        adapter.addFooter(footer1);
//        adapter.addFooter(footer2);
//        adapter.addFooter(footer3);

        // 添加item分隔条
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
        ListView s;
        s.addHeaderView();
    }
}