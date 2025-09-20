package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class logactivity extends AppCompatActivity {
    int userId;
    String selectedActivity;
    DBHelper dbHelper;
    TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logactivity);

        dbHelper = DBHelper.getInstance(this);
        question = findViewById(R.id.question);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
            selectedActivity = extras.getString("selectedActivity");

            String[] habitInfo = dbHelper.getHabitInfo(selectedActivity);
            if (habitInfo != null) {
                question.setText(habitInfo[1]); // Set the question text
            } else {
                Toast.makeText(this, "Habit not found", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "User ID not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Add onClickListener for "Yes" button
        findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLogEntry(userId, selectedActivity, 1);
            }
        });

        // Add onClickListener for "No" button
        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLogEntry(userId, selectedActivity, 0);
            }
        });
    }

    private void addLogEntry(int userId, String selectedActivity, int status) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("ss");
        String currentTime = timeFormat.format(new Date());

        // Get the habit ID based on the selected activity
        int habitId = dbHelper.getCommonHabitID(selectedActivity);

        // Insert the log entry into the database
        long result = dbHelper.insertLogEntry(userId, habitId, status, currentTime);

        if (result != -1) {
            Toast.makeText(this, "Log entry added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add log entry", Toast.LENGTH_SHORT).show();
        }
    }
}
