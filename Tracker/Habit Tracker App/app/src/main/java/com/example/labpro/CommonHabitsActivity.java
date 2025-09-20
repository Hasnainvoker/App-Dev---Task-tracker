package com.example.labpro;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommonHabitsActivity extends AppCompatActivity {
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_habits);

        // Retrieve userId from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId", -1); // Default value of -1 if userId is not found
        } else {
            userId = -1; // Handle the case where extras is null
        }

        MyListData[] myListData = new MyListData[] {
                new MyListData("Health", R.drawable.health),
                new MyListData("Hygiene", R.drawable.hygiene),
                new MyListData("Productivity", R.drawable.productivity),
                new MyListData("Sports", R.drawable.sports),
        };

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        // Pass 'this' as the context and userId to the adapter
        MyListAdapter adapter = new MyListAdapter(myListData, this, userId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
