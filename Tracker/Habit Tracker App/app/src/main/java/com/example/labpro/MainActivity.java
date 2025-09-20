package com.example.labpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button button_login , button_signup ;
    TextView textView ;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            button_login = (Button) findViewById(R.id.button_login);
            button_signup = (Button) findViewById(R.id.button_signup);
            textView = (TextView) findViewById(R.id.text_quote);
            String[] quotes = {
                    "The secret of your future is hidden in your daily routine. - Mike Murdock",
                    "We are what we repeatedly do. Excellence, then, is not an act, but a habit. - Aristotle",
                    "The chains of habit are too weak to be felt until they are too strong to be broken. - Samuel Johnson",
                    "Success is the sum of small efforts repeated day in and day out. - Robert Collier",
                    "You will never change your life until you change something you do daily. The secret of your success is found in your daily routine. - John C. Maxwell",
                    "The first step towards change is awareness. The second step is acceptance. - Nathaniel Branden",
                    "Motivation is what gets you started. Habit is what keeps you going. - Jim Ryun",
                    "What you get by achieving your goals is not as important as what you become by achieving your goals. - Zig Ziglar",
                    "Small daily improvements are the key to staggering long-term results. - Unknown",
                    "The only way to do great work is to love what you do. If you haven't found it yet, keep looking. Don't settle. - Steve Jobs"
            };

            // Random number generator
            Random random = new Random();

            // Generate a random index to select a quote
            int randomIndex = random.nextInt(quotes.length);

            // Retrieve and print the randomly selected quote
            String selectedQuote = "'" + quotes[randomIndex] + "'";
            textView.setText(selectedQuote);
            button_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), login.class);
                    startActivity(i);
                }
            });

            button_signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), signup.class);
                    startActivity(i);
                }
            });
        }
}

