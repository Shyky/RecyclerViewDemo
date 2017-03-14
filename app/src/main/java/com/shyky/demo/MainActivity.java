package com.shyky.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(ListViewFunctionsActivity.class);
                        break;
                    case 1:
                        startActivity(GridViewFunctionsActivity.class);
                        break;
                    case 2:
                        startActivity(ExpandableListViewFunctionsActivity.class);
                        break;
                    case 3:
                        startActivity(RecyclerViewFunctionsActivity.class);
                        break;
                    case 4:
                        startActivity(SmartRecyclerViewFunctionsActivity.class);
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