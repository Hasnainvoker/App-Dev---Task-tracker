package com.example.labpro;

import static com.example.labpro.R.id.listView1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class progress_listview extends AppCompatActivity {
    int userId;
    DBHelper dbHelper;
    ListView listView1;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_listview);

        // Get user ID from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        } else {
            // Handle case when extras bundle is null
            // For example, display a message and finish the activity
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        dbHelper = new DBHelper(this);
        listView1= findViewById(R.id.listView1);

        // Fetch activities associated with the user ID from the database
        ArrayList<String> activities = dbHelper.getUserActivities(userId);

        // Populate the activities into the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, activities);
        listView1.setAdapter(adapter);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String value = adapter.getItem(position);
                //Toast.makeText(getApplicationContext(), value, Toast.LENGTH_SHORT).show();

                int commonHabitID = dbHelper.getCommonHabitID(value);
                // Assuming commonHabitID is obtained from somewhere
                Intent i = new Intent(getApplicationContext(), graph.class);
                i.putExtra("userId", userId);
                startActivity(i);
            }
        });
    }

}