package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class admin_addCommunityChallenges extends AppCompatActivity {

    EditText editTextName, editTextDescription, editTextPlace, editTextTime;
    DatePicker datePicker;
    Button buttonAdd;

    // Declare a DBHelper object
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_community_challenges);

        // Initialize DBHelper object
        dbHelper = new DBHelper(this);

        // Initialize views
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextPlace = findViewById(R.id.editTextPlace);
        datePicker = findViewById(R.id.datePicker);
        buttonAdd = findViewById(R.id.buttonAdd);

        // Set onClickListener for the "ADD" button
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get input values
                String name = editTextName.getText().toString().trim();
                String description = editTextDescription.getText().toString().trim();
                String place = editTextPlace.getText().toString().trim();
                // Fetch time from EditText
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1; // Month is zero-based, so add 1
                int year = datePicker.getYear();

                // Construct the date string
                String date = day + "/" + month + "/" + year;

                // Insert data into the community_challenges table
                long result = dbHelper.insertCommunityChallenge(name, description, date, place);
                if (result != -1) {
                    Toast.makeText(admin_addCommunityChallenges.this, "Challenge added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(admin_addCommunityChallenges.this, "Failed to add challenge", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
