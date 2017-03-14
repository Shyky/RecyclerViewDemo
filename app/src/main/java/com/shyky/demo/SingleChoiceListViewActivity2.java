package com.shyky.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView single choice
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/20
 * @since 1.0
 */
public class SingleChoiceListViewActivity2 extends AppCompatActivity {
    private static class ViewHolder {
        private TextView textView;
        private RadioButton radioButton;
    }

    private class SingleChoiceAdapter extends BaseAdapter {
        private final LayoutInflater layoutInflater;
        private List<String> data;
        private int currentCheckedItemPosition;

        public SingleChoiceAdapter(Context context, List<String> data) {
            layoutInflater = LayoutInflater.from(context);
            this.data = data;
            // 默认为-1，没有选择任何item
            currentCheckedItemPosition = AbsListView.INVALID_POSITION; // -1
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_single_choice, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.radioButton = (RadioButton) convertView.findViewById(R.id.radioButton);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (currentCheckedItemPosition == position) {
                viewHolder.radioButton.setChecked(true);
            } else {
                viewHolder.radioButton.setChecked(false);
            }
            viewHolder.textView.setText(getItem(position));

            return convertView;
        }

        public void setDefaultCheckedItemPosition(int position) {
            currentCheckedItemPosition = position;
        }

        public int getCheckedItemPosition() {
            return currentCheckedItemPosition;
        }

        public void check(int position) {
            setDefaultCheckedItemPosition(position);
            notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_simple);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 10; j++) {
            data.add("I am Text " + (j + 1));
        }

        final SingleChoiceAdapter adapter = new SingleChoiceAdapter(this, data);
        // 默认选中第一个item
        adapter.setDefaultCheckedItemPosition(0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = listView.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", data.get(position - headerViewsCount));
                }
                adapter.check(position);
                Toast.makeText(getApplicationContext(), "Click item position = " + (position + 1) + " , Current checked item position = " + adapter.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}