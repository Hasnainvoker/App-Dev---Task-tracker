package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CustomHabitsActivity extends AppCompatActivity {

    int userId;
    EditText habitNameEditText, questionEditText, frequencyEditText, notesEditText;
    RadioGroup reminderRadioGroup;
    Button setButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_habits);

        // Get user ID from intent extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        } else {
            // Handle the case where user ID is not provided
            Toast.makeText(getApplicationContext(), "User ID not provided", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activityca if user ID is not provided
        }

        // Find views by ID
        habitNameEditText = findViewById(R.id.editTexthabitname);
        questionEditText = findViewById(R.id.editTextquestion);
        frequencyEditText = findViewById(R.id.editTextfrequency);
        notesEditText = findViewById(R.id.editTextNotes);
        reminderRadioGroup = findViewById(R.id.radioGroupReminder);
        setButton = findViewById(R.id.button_set);

        // Set OnClickListener for the "SET" button
        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get text from EditText fields
                String habitName = habitNameEditText.getText().toString();
                String question = questionEditText.getText().toString();
                String frequency = frequencyEditText.getText().toString();
                String notes = notesEditText.getText().toString();

                // Get selected radio button option for reminder
                String reminder = ((RadioButton) findViewById(reminderRadioGroup.getCheckedRadioButtonId())).getText().toString();

                // Determine if the reminder is set or not
                int reminderValue = reminder.equals("Yes") ? 1 : 0;
                // Inside CustomHabitsActivity
                DBHelper dbHelper = DBHelper.getInstance(getApplicationContext());

// Inside onClick method of setButton OnClickListener
                long habitID = dbHelper.insertCustomHabit(habitName, question, frequency, notes, reminderValue);
                if (habitID != -1) {
                    Toast.makeText(getApplicationContext(), "Habit created successfully", Toast.LENGTH_SHORT).show();
                    long result = dbHelper.insertUserHabitForCustom(userId, habitID);
                    if (result != -1) {
                        Toast.makeText(getApplicationContext(), "Habit added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to add habit to UserHabits table", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to create habit", Toast.LENGTH_SHORT).show();
                }

                // Redirect to MainHome activity
                Intent intent = new Intent(getApplicationContext(), mainhome.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

            }
        });
    }

    private long insertCustomHabit(String habitName, String question, String frequency, String notes, int reminder) {
        // Get a writable database instance
        SQLiteDatabase db = DBHelper.getInstance(getApplicationContext()).getWritableDatabase();
        // Prepare content values for insertion
        ContentValues values = new ContentValues();
        values.put("habitName", habitName);
        values.put("question", question);
        values.put("frequency", frequency);
        values.put("notes", notes);
        values.put("reminder", reminder);
        // Perform the insertion and get the inserted row ID
        long habitID = db.insert("CustomHabits", null, values);
        // Close the database connection
        db.close();
        // Return the inserted row ID or -1 if insertion fails
        return habitID;
    }

    private long insertUserHabitForCustom(int userId, long customHabitID) {
        // Get a writable database instance
        SQLiteDatabase db = DBHelper.getInstance(getApplicationContext()).getWritableDatabase();
        // Prepare content values for insertion
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.putNull("commonHabitID"); // Set commonHabitID as null
        values.put("customHabitID", customHabitID);
        // Perform the insertion and get the inserted row ID
        long result = db.insert("UserHabits", null, values);
        // Close the database connection
        db.close();
        // Return the inserted row ID or -1 if insertion fails
        return result;
    }
}
