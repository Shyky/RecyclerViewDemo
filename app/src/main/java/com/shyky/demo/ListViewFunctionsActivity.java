package com.shyky.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ListViewFunctionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 9:
                        startActivity(SingleChoiceListViewActivity.class);
                        break;
                    case 10:
                        startActivity(SingleChoiceListViewActivity2.class);
                        break;
                    case 11:
                        startActivity(MultipleChoiceListViewActivity.class);
                        break;
                    case 12:
                        startActivity(MultipleChoiceListViewActivity2.class);
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