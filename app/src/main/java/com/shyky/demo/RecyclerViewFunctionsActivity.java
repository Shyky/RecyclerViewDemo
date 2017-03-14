package com.shyky.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class RecyclerViewFunctionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_entries_recycler_view);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(SimpleRecyclerViewActivity.class);
                        break;
                    case 1:
                        startActivity(DividerItemDecorationRecyclerViewActivity.class);
                        break;
                    case 9:
                        startActivity(SingleChoiceRecyclerViewActivity.class);
                        break;
                    case 10:
                        startActivity(MultipleChoiceRecyclerViewActivity.class);
                        break;
                    case 11:
                        startActivity(MultipleHeaderFooterRecyclerViewActivity.class);
                        break;
                }
            }
        });
    }

    private void startActivity(Class clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }
}