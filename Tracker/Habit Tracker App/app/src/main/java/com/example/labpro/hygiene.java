package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class hygiene extends AppCompatActivity {
    ListView listView;
    TextView textView;
    String[] listItem;
    int userId;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hygiene);
        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");

        dbHelper = new DBHelper(this); // Initialize DBHelper
        listView = (ListView) findViewById(R.id.listView);
        textView = (TextView) findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.hygiene);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get the selected habit name
                String habitName = adapter.getItem(position);
                // Get the corresponding commonHabitID from the database using the habitName
                int commonHabitID = dbHelper.getCommonHabitID(habitName);
                // Insert the habit into UserHabits table
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
