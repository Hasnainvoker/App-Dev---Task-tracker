package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class health extends AppCompatActivity {
    ListView listView;
    TextView textView;
    String[] listItem;
    int userId;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        dbHelper = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

        listItem = getResources().getStringArray(R.array.health);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

                int commonHabitID = dbHelper.getCommonHabitID(value);
                // Assuming commonHabitID is obtained from somewhere
                long result = dbHelper.insertUserHabitForCommon(userId, commonHabitID);
                if (result != -1) {
                    Toast.makeText(getApplicationContext(), "Habit added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to add habit", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
