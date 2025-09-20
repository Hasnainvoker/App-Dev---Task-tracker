package com.example.labpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {
    int userId;
    Button button_commonHabits;
    Button button_customHabits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle extras = getIntent().getExtras();
        userId = extras.getInt("userId");

        button_commonHabits = findViewById(R.id.commonHabitButton);
        button_customHabits = findViewById(R.id.customHabitButton);

        System.out.println("User ID home: " + userId);
        button_commonHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCommonHabitsPage();
            }
        });

        button_customHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCustomHabitsPage();
            }
        });


    }

    private void openCommonHabitsPage() {
        Intent intent = new Intent(this, CommonHabitsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    private void openCustomHabitsPage() {
        Intent intent = new Intent(this, CustomHabitsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }


}
