package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class notify extends AppCompatActivity {

    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");

        TimePicker timePicker = findViewById(R.id.timePicker);
        Button addButton = findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected hour and minute from the TimePicker
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                // Convert selected hour and minute into HH:mm format
                String time = String.format("%02d:%02d", hour, minute);

                // Insert the selected time into the Notify table
                long result = DBHelper.getInstance(notify.this).insertNotification(userId, time);

                if (result != -1) {
                    Toast.makeText(notify.this, "Notification added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(notify.this, "Failed to add notification", Toast.LENGTH_SHORT).show();
                }

                // Finish the activity and return to the previous activity
                Intent i = new Intent(getApplicationContext(), mainhome.class);
                i.putExtra("userId", userId);
                startActivity(i);
            }
        });
    }
}
