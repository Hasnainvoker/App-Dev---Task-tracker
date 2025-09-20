// userchallenge.java
package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class userchallenge extends AppCompatActivity {

    DBHelper dbHelper;
    int userId;
    String challengeName;
    int challengeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userchallenge);

        dbHelper = new DBHelper(this);

        // Receive the intent
        Intent intent = getIntent();
        if (intent != null) {
            // Get the user ID, challenge name, and description from the intent
            userId = intent.getIntExtra("userId", -1);
            challengeName = intent.getStringExtra("challengeName");
            String description = intent.getStringExtra("description");
            challengeId = intent.getIntExtra("challengeId", -1);
            if (challengeName != null && description != null) {
                // Find the TextView for the description
                TextView descriptionTextView = findViewById(R.id.discription);

                // Set the description text
                descriptionTextView.setText(description + "\n Did you Participate ??");
            }
        }

        Button yesButton = findViewById(R.id.yes);
        Button noButton = findViewById(R.id.no);

        // Set click listeners for Yes and No buttons
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert "Yes" for the challenge
                long result = dbHelper.insertUserChallenge(userchallenge.this, userId, challengeId, true, 10);
                if (result != -1) {
                    Toast.makeText(userchallenge.this, "Challenge completed successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(userchallenge.this, "Already participated in this challenge.", Toast.LENGTH_SHORT).show();
                }
                finish(); // Finish the activity and go back to the previous one
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Insert "No" for the challenge
                long result = dbHelper.insertUserChallenge(userchallenge.this, userId, challengeId, false, 0);
                if (result != -1) {
                    Toast.makeText(userchallenge.this, "Challenge status updated successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(userchallenge.this, "Already participated in this challenge.", Toast.LENGTH_SHORT).show();
                }
                finish(); // Finish the activity and go back to the previous one
            }
        });
    }
}
