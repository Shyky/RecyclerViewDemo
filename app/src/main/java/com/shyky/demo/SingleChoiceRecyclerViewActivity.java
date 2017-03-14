package com.shyky.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class SingleChoiceRecyclerViewActivity extends AppCompatActivity {
    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }

    public class InternalViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RadioButton radioButton;

        public InternalViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
            radioButton = (RadioButton) itemView.findViewById(R.id.radioButton);
        }
    }

    private class SingleChoiceAdapter extends RecyclerView.Adapter<InternalViewHolder> {
        private final LayoutInflater layoutInflater;
        private List<String> data;
        /**
         * 默认为-1，没有选择任何item
         */
        private int currentCheckedItemPosition;
        private OnItemClickListener onItemClickListener;

        public SingleChoiceAdapter(Context context, List<String> data) {
            layoutInflater = LayoutInflater.from(context);
            this.data = data;
            currentCheckedItemPosition = -1;
        }

        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public InternalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new InternalViewHolder(layoutInflater.inflate(R.layout.item_single_choice, parent, false));
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

            if (currentCheckedItemPosition == position) {
                holder.radioButton.setChecked(true);
            } else {
                holder.radioButton.setChecked(false);
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

        public void setOnItemClickListener(@NonNull OnItemClickListener listener) {
            onItemClickListener = listener;
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

        final SingleChoiceAdapter adapter = new SingleChoiceAdapter(this, data);
        // 默认选中第一个item
        adapter.setDefaultCheckedItemPosition(0);
        // 这个方法不能忘，指定显示布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 添加分隔线，DividerItemDecoration这个类是系统提供的，在support包中
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                adapter.check(position);
                Toast.makeText(getApplicationContext(), "Click item position = " + (position + 1) + " , Current checked item position = " + adapter.getCheckedItemPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}