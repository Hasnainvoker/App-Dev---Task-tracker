package com.example.labpro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class myactivities extends AppCompatActivity {
    int userId;
    DBHelper dbHelper;
    ListView listView;
    Button removeButton;
    ArrayList<String> selectedActivities;
    ArrayAdapter<String> adapter;

    boolean isSelectMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myactivities);

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
        listView = findViewById(R.id.listView);
        removeButton = findViewById(R.id.removeButton);
        selectedActivities = new ArrayList<>();

        // Fetch activities associated with the user ID from the database
        ArrayList<String> activities = dbHelper.getUserActivities(userId);

        // Populate the activities into the ListView
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_activated_1, activities);
        listView.setAdapter(adapter);

        // Set OnClickListener for the remove button
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSelectMode) {
                    // If not in select mode, switch to select mode
                    isSelectMode = true;
                    removeButton.setText("Delete");
                    // Enable multiple choice mode for ListView
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                } else {
                    // If in delete mode, perform deletion
                    // If no activities are selected, show a toast message and return
                    if (selectedActivities.isEmpty()) {
                        Toast.makeText(myactivities.this, "No activities selected", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Show an alert dialog to confirm deletion
                    AlertDialog.Builder builder = new AlertDialog.Builder(myactivities.this);
                    builder.setTitle("Confirm Deletion");
                    builder.setMessage("Are you sure you want to delete the selected habits?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Remove selected activities from the database
                            for (String activity : selectedActivities) {
                                dbHelper.deleteUserHabit(userId, activity);
                            }
                            // Show a toast message to indicate removal
                            Toast.makeText(myactivities.this, "Selected activities removed", Toast.LENGTH_SHORT).show();
                            // Finish the current activity and start it again to refresh
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Finish the current activity and start it again to refresh
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    builder.show();
                }
            }
        });

        // Set item click listener for ListView to select/deselect items or navigate to the next activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isSelectMode) {
                    // If not in select mode, start the next activity
                    String selectedActivity = (String) parent.getItemAtPosition(position);

                    // Here, start the activity you want to navigate to
                    // For example, if you want to start 'NextActivity', you can do:
                    Intent intent = new Intent(myactivities.this, logactivity.class);
                    // Pass any data you need to the next activity
                    intent.putExtra("userId", userId);
                    intent.putExtra("selectedActivity", selectedActivity);
                    startActivity(intent);
                } else {
                    // If in select mode, toggle selection of items
                    String activity = (String) parent.getItemAtPosition(position);
                    if (selectedActivities.contains(activity)) {
                        selectedActivities.remove(activity);
                        view.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    } else {
                        selectedActivities.add(activity);
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    }
                }
            }
        });
    }
}
