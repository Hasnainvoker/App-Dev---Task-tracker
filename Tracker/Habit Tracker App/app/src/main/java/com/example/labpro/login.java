package com.example.labpro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button loginButton;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize EditText fields and the login button
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set a click listener for the loginButton
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the text from EditText fields
                String enteredUsername = username.getText().toString();
                String enteredPassword = password.getText().toString();

                // Check if the username and password exist in the database
                if (isValidUser(enteredUsername, enteredPassword)) {
                    // If valid, retrieve the user ID
                    int userId = getuserId(enteredUsername);
                    if(enteredUsername.equals("admin") && enteredPassword.equals("1234")) {
                        Intent intent = new Intent(getApplicationContext(), admin_homepage.class);
                        startActivity(intent);
                    } else if (userId != -1) {
                        // Start the main home activity with the user ID
                        System.out.println("User ID before sending : " + userId);
                        Intent intent = new Intent(getApplicationContext(), mainhome.class);
                        intent.putExtra("userId", userId);
                        startActivity(intent);

                        // Print the userId
                        System.out.println("User ID after sending: " + userId);
                    } else {
                        // Handle the case when the user ID is not found
                        Toast.makeText(login.this, "Failed to retrieve user ID", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // Handle the case when the username or password is invalid
                    Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Method to check if the username and password exist in the database
    private boolean isValidUser(String username, String password) {
        if (username.equals("admin") && password.equals("1234")) {
            return true;
        }
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE username = ? AND password = ?",
                new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    // Method to retrieve the user ID based on the username
    @SuppressLint("Range")
    private int getuserId(String username) {
        int userId = -1;
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT userId FROM Users WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex("userId"));
        }
        cursor.close();
        return userId;
    }
}
