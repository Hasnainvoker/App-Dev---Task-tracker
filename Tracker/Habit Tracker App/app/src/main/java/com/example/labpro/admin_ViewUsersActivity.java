package com.example.labpro;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class admin_ViewUsersActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);

        dbHelper = new DBHelper(this);
        listView = findViewById(R.id.listView);

        // Fetch all users' info from the DBHelper
        ArrayList<String> usersInfo = dbHelper.getAllUsersInfo();

        // Populate the users' info into the ListView using an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, usersInfo);
        listView.setAdapter(adapter);
    }
}
