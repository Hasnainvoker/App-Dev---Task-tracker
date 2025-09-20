package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin_homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        // Initialize buttons
        Button btnAddCommunityChallenges = findViewById(R.id.btnAddCommunityChallenges);
        Button btnViewUsers = findViewById(R.id.btnViewUsers);


        // Set onClickListener for "Add Community Challenges" button
        btnAddCommunityChallenges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Add Community Challenges activity
                Intent intent = new Intent(admin_homepage.this, admin_addCommunityChallenges.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for "View Users" button
        btnViewUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open View Users activity
                Intent intent = new Intent(admin_homepage.this, admin_ViewUsersActivity.class);
                startActivity(intent);
            }
        });

        // Set onClickListener for "Delete Users" button

    }
}
