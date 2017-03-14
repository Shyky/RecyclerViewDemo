package com.shyky.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * ListView multiple choice
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public class MultipleChoiceListViewActivity2 extends AppCompatActivity {
    private static class ViewHolder {
        private TextView textView;
        private CheckBox checkBox;
    }

    private class MultipleChoiceAdapter extends BaseAdapter {
        private final LayoutInflater layoutInflater;
        private List<String> data;
        private SparseBooleanArray checkStates;

        public MultipleChoiceAdapter(Context context, List<String> data) {
            layoutInflater = LayoutInflater.from(context);
            this.data = data;
            // 默认没有选择任何item
            checkStates = new SparseBooleanArray(0);
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
                convertView = layoutInflater.inflate(R.layout.item_multiple_choice, parent, false);
                viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (checkStates.get(position)) {
                viewHolder.checkBox.setChecked(true);
            } else {
                viewHolder.checkBox.setChecked(false);
            }
            viewHolder.textView.setText(getItem(position));

            return convertView;
        }

        public void setDefaultCheckedItemPositions(int... positions) {
            for (int item : positions) {
                checkStates.put(item, true);
            }
        }

        public void check(int position) {
            // 如果当前item已选中了，则从集合中删除，否则选择该item
            if (checkStates.get(position)) {
                checkStates.delete(position);
            } else
                checkStates.put(position, true);
            notifyDataSetChanged();
        }

        public SparseBooleanArray getCheckedItemPositions() {
            return checkStates;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_simple);
        final ListView listView = (ListView) findViewById(R.id.listView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            data.add("I am Text " + (j + 1));
        }

        final MultipleChoiceAdapter adapter = new MultipleChoiceAdapter(this, data);
        // 默认选中第一和三个item
        adapter.setDefaultCheckedItemPositions(0, 2);
        listView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "默认选中项：" + print(adapter.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int headerViewsCount = listView.getHeaderViewsCount();
                if (position >= headerViewsCount) {
                    Log.d("Test", data.get(position - headerViewsCount));
                }
                adapter.check(position);
                Toast.makeText(getApplicationContext(), "onItemClick position = " + (position + 1) + " , Checked item positions = " + print(adapter.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String print(SparseBooleanArray booleanArray) {
        final int size = booleanArray.size();
        if (size <= 0) {
            return "{}";
        }

        StringBuilder buffer = new StringBuilder(size * 28);
        buffer.append('{');
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                buffer.append(", ");
            }
            int key = booleanArray.keyAt(i);
            buffer.append(key);
        }
        buffer.append('}');
        return buffer.toString();
    }
}