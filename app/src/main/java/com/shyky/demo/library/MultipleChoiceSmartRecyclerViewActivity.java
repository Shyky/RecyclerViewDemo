package com.shyky.demo.library;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Toast;

import com.shyky.demo.MultipleChoiceRecyclerView;
import com.shyky.demo.R;
import com.shyky.library.adapter.ArrayChoiceModeAdapter;
import com.shyky.library.adapter.ChoiceModeAdapter;

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
public class MultipleChoiceSmartRecyclerViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_recycler_view);
        final MultipleChoiceRecyclerView recyclerView = (MultipleChoiceRecyclerView) findViewById(R.id.recyclerView);
        final List<String> data = new ArrayList<>();
        for (int j = 0; j < 15; j++) {
            data.add("I am Text " + (j + 1));
        }

        // 默认选中第一、三、五个item
        recyclerView.setCheckedItemPositions(0, 2, 4);
        recyclerView.setChoiceMode(ChoiceModeAdapter.CHOICE_MODE_MULTIPLE);
        // 这个方法不能忘，指定显示布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 添加分隔线，DividerItemDecoration这个类是系统提供的，在support包中
        // recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new ArrayChoiceModeAdapter(this, data));

        Toast.makeText(getApplicationContext(), "默认选中项：" + print(recyclerView.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();

        recyclerView.setOnItemClickListener(new ChoiceModeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, long id) {
                recyclerView.check(position);
                Toast.makeText(getApplicationContext(), "Click item position = " + (position + 1) + " , Checked item positions = " + print(recyclerView.getCheckedItemPositions()), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String print(SparseBooleanArray booleanArray) {
        final int size = booleanArray == null ? 0 : booleanArray.size();
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