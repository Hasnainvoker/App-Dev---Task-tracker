package com.example.labpro;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {

    private EditText editTextPhoneNumber, editTextUsername, editPassword, editTextEmail;
    private Button validateButton;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize EditText fields and the button
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextUsername = findViewById(R.id.editTextUsername);
        editPassword = findViewById(R.id.editPassword);
        validateButton = findViewById(R.id.buttonRegister);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set a click listener for the validateButton
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the text from EditText fields
                String phoneNumber = editTextPhoneNumber.getText().toString();
                String email = editTextEmail.getText().toString();
                String username = editTextUsername.getText().toString();
                String password = editPassword.getText().toString();

                // Validate phone number and email address
                if (isValidPhoneNumber(phoneNumber) && isValidEmail(email)) {
                    // Check if the user already exists in the database
                    if (!userExists(username, email)) {
                        // User does not exist, insert into the database
                        if (insertUser(username, email, password, phoneNumber) != -1) {
                            Toast.makeText(signup.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), login.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(signup.this, "Failed to register user", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(signup.this, "User already exists", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signup.this, "Invalid phone number or email address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Phone number validation method
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for a valid phone number (assuming a 10-digit format)
        String regex = "\\d{10}";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher with the given phone number
        Matcher matcher = pattern.matcher(phoneNumber);
        // Return true if the phone number matches the regex pattern, false otherwise
        return matcher.matches();
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        // Regular expression for a valid email address
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);
        // Create a matcher with the given email address
        Matcher matcher = pattern.matcher(email);
        // Return true if the email address matches the regex pattern, false otherwise
        return matcher.matches();
    }

    // Method to check if a user already exists in the Users table
    private boolean userExists(String username, String email) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE username = ? OR email = ?",
                new String[]{username, email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to insert a new user into the Users table
    private long insertUser(String username, String email, String password, String phoneNumber) {
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("email", email);
        values.put("password", password);
        values.put("phone_number", phoneNumber); // Insert phone number
        return db.insert("Users", null, values);
    }

    @Override
    protected void onDestroy() {
        // Close the database when the activity is destroyed
        if (db != null) {
            db.close();
        }
        super.onDestroy();
    }
}
