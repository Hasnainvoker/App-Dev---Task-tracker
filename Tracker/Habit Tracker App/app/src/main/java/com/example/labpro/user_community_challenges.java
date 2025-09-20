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

public class user_community_challenges extends AppCompatActivity {

    DBHelper dbHelper;
    int userId;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_community_challenges);
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
        listView = findViewById(R.id.listView_comm);

        // Fetch activities associated with the user ID from the database
        ArrayList<String> activities = dbHelper.getCommunityChallenges();

        // Populate the activities into the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, activities);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Retrieve the selected challenge name and description
                String challengeName = adapter.getItem(position);
                String description = activities.get(position); // Assuming description is stored along with the challenge name in activities list
                // Use the position as the challenge ID
                int challengeId = position + 1; // Add 1 because position starts from 0 but challenge IDs start from 1

                // Create an intent to navigate to the userchallenge.class activity
                Intent intent = new Intent(user_community_challenges.this, userchallenge.class);
                // Pass the user ID, challenge name, description, and challenge ID to the userchallenge activity
                intent.putExtra("userId", userId);
                intent.putExtra("challengeName", challengeName);
                intent.putExtra("description", description);
                intent.putExtra("challengeId", challengeId);
                startActivity(intent);
            }
        });

        // Initialize the button
        Button pointsButton = findViewById(R.id.points);
        // Set an OnClickListener to the button
        pointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Call a method to retrieve the total points for the user
                int totalPoints = dbHelper.getTotalPointsForUser(userId);
                // Show an alert with the total points
                showTotalPointsAlert(totalPoints);
            }
        });
    }

    // Method to show an alert with the total points
    private void showTotalPointsAlert(int totalPoints) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Total Points");
        builder.setMessage("Total Points: " + totalPoints);
        // Set a Close button
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Close the dialog
                dialogInterface.dismiss();
            }
        });
        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
