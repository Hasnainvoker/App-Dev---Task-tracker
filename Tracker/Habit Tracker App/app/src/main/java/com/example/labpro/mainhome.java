package com.example.labpro;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import java.util.Calendar;

public class mainhome extends AppCompatActivity {

    // UI elements
    private Button button_add, button_check, button_out, button_comm, addnotification;
    private TextView textView;
    Button button_myactivities;

    // User ID
    private int userId;

    // Alert dialog builder
    private AlertDialog.Builder builder;

    // Notification channel ID
    private static final String CHANNEL_ID = "my_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainhome);

        // Retrieve user ID from previous intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getInt("userId");
        }

        // Initialize UI elements
        button_add = findViewById(R.id.button_add);
        button_check = findViewById(R.id.button_check);
        button_out = findViewById(R.id.button_out);
        button_comm = findViewById(R.id.button_com);
        addnotification = findViewById(R.id.addnotification);
        textView = findViewById(R.id.textView);

        button_myactivities = findViewById(R.id.button_myactivities);
        // Initialize alert dialog builder
        builder = new AlertDialog.Builder(this);

        // Set onClickListeners for buttons
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHomeActivity();
            }
        });
        button_myactivities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyActivitiesPage();
            }
        });
        addnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNotifyActivity();
            }
        });

        button_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignOutDialog();
            }
        });

        button_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGraphActivity();
            }
        });

        button_comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUserCommunityChallengesActivity();
            }
        });

        // Create notification channel
        createNotificationChannel();

        // Start showing notifications periodically
        startNotificationTimer(userId);
    }

    // Create notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "My Channel";
            String description = "My Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Show sign out confirmation dialog
    private void showSignOutDialog() {
        builder.setMessage("Confirm Signout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        signOut();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle("Alert");
        alert.show();
    }

    // Sign out
    private void signOut() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }

    // Start home activity with user ID
    private void startHomeActivity() {
        Intent i = new Intent(getApplicationContext(), home.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    // Start notify activity with user ID
    private void startNotifyActivity() {
        Intent i = new Intent(getApplicationContext(), notify.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    // Start graph activity with user ID
    private void startGraphActivity() {
        Intent i = new Intent(getApplicationContext(), graph.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    // Start user community challenges activity with user ID
    private void startUserCommunityChallengesActivity() {
        Intent i = new Intent(getApplicationContext(), user_community_challenges.class);
        i.putExtra("userId", userId);
        startActivity(i);
    }

    // Start showing notifications periodically
    private void startNotificationTimer(int userId) {
        DBHelper dbHelper = DBHelper.getInstance(this);
        String notificationTime = dbHelper.getNotificationTime(userId);
        if (notificationTime != null) {
            String[] timeParts = notificationTime.split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if (calendar.getTimeInMillis() < System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showNotification();
                }
            }, calendar.getTimeInMillis() - System.currentTimeMillis());
        }
    }
    private void openMyActivitiesPage() {
        Intent intent = new Intent(this, myactivities.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }
    // Show notification
    private void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Intent to launch MyActivities class when user presses Yes
        Intent yesIntent = new Intent(this, myactivities.class);
        yesIntent.putExtra("userId", userId);
        PendingIntent pendingYesIntent = PendingIntent.getActivity(
                this, 0, yesIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Intent to launch MainActivity class when user presses No
        Intent noIntent = new Intent(this, mainhome.class);
        noIntent.putExtra("userId", userId);
        PendingIntent pendingNoIntent = PendingIntent.getActivity(
                this, 1, noIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                this, CHANNEL_ID);
        builder.setContentTitle("Hello");
        builder.setContentText("Time To Log Your Activities");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.addAction(R.drawable.ic_launcher_foreground, "Yes", pendingYesIntent);
        builder.addAction(R.drawable.ic_launcher_foreground, "No", pendingNoIntent);

        // Show the notification
        manager.notify(1, builder.build());
    }
}
