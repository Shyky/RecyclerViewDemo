package com.shyky.demo.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.shyky.demo.R;
import com.shyky.library.adapter.base.BaseRecyclerViewAdapter;

import java.util.List;

/**
 * @author Shyky
 * @version 1.1
 * @date 2017/1/24
 * @since 1.0
 */
public class TestAdapter extends BaseRecyclerViewAdapter<String> {
    public TestAdapter(@NonNull Context context) {
        super(context);
    }

    public TestAdapter(@NonNull Context context, @NonNull List<String> data) {
        super(context, data);
    }

//    @Override
//    public int getHeaderItemLayoutResId() {
//        return R.layout.item_header;
//    }

//    @Override
//    public int[] getHeaderItemLayoutResIds() {
//        return new int[]{R.layout.item_header, R.layout.item_header};
//    }

    @Override
    public int getFooterItemLayoutResId() {
        return R.layout.item_footer;
    }

    @Override
    public int getItemLayoutResId(int viewType) {
        return R.layout.item_text;
    }

    @Override
    public void bindViewHolder(@NonNull GenericRecyclerViewHolder viewHolder, int viewType, int position) {
        viewHolder.setText(viewType, android.R.id.title, getItem(position));
    }

    @Override
    protected void onItemClick(View view, int viewResId, int position) {
        Toast.makeText(context, position, Toast.LENGTH_SHORT).show();
    }
}