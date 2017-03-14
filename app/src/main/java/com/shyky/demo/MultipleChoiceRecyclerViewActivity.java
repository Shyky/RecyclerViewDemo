package com.shyky.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView multiple choice
 *
 * @author Shyky
 * @version 1.1
 * @date 2017/1/21
 * @since 1.0
 */
public class MultipleChoiceRecyclerViewActivity extends AppCompatActivity {
    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    public class InternalViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CheckBox checkBox;

        public InternalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }
    }

    private class MultipleChoiceAdapter extends RecyclerView.Adapter<InternalViewHolder> {
        private final LayoutInflater layoutInflater;
        private List<String> data;
        private OnItemClickListener onItemClickListener;
        private SparseBooleanArray checkStates;

        public MultipleChoiceAdapter(Context context, List<String> data) {
            layoutInflater = LayoutInflater.from(context);
            this.data = data;
            // 默认没有选择任何item
            checkStates = new SparseBooleanArray(0);
        }

        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public InternalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InternalViewHolder(layoutInflater.inflate(R.layout.item_multiple_choice, parent, false));
        }

        @Override
        public void onBindViewHolder(InternalViewHolder holder, final int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, position, getItemId(position));
                    }
                }
            });

            if (checkStates.get(position)) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
            holder.textView.setText(getItem(position));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
            onItemClickListener = listener;
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
        setContentView(R.layout.activity_recycler_view);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            data.add("I am Text " + (j + 1));
        }

        final MultipleChoiceAdapter adapter = new MultipleChoiceAdapter(this, data);
        // 默认选中第一、三、五个item
        adapter.setDefaultCheckedItemPositions(0, 2, 4);
        // 这个方法不能忘，指定显示布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 添加分隔线，DividerItemDecoration这个类是系统提供的，在support包中
        // recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        Toast.makeText(getApplicationContext(), "默认选中项：" + print(adapter.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                adapter.check(position);
                Toast.makeText(getApplicationContext(), "Click item position = " + (position + 1) + " , Checked item positions = " + print(adapter.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();
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