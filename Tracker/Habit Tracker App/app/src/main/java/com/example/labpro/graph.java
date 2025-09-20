package com.example.labpro;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;

public class graph extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Get a reference to the LineChart widget
        lineChart = findViewById(R.id.line_chart);

        // Get the userId from the intent extras
        int userId = getIntent().getIntExtra("userId", -1);
        if (userId == -1) {
            // Handle the case where userId is not provided
            // For example, you can display an error message or finish the activity
            // In this example, I'm just logging an error message and finishing the activity
            Log.e("GraphActivity", "User ID is not provided");
            finish();
            return;
        }

        // Get the log data for the given user
        ArrayList<HashMap<String, String>> logEntries = getLogEntries(userId);

        // Create entries for the LineChart
        ArrayList<Entry> entries = new ArrayList<>();
        int time = 0;
        for (HashMap<String, String> entry : logEntries) {
            // Convert status to float
            float status = Float.parseFloat(entry.get("status"));
            entries.add(new Entry(time++, status));
        }

        // Create a LineDataSet from the entries
        LineDataSet dataSet = new LineDataSet(entries, "Habit Status");

        // Set colors for the line
        dataSet.setColor(getResources().getColor(R.color.yellow));
        dataSet.setCircleColor(getResources().getColor(R.color.color_blue));

        // Create a LineData object and add the dataSet to it
        LineData lineData = new LineData(dataSet);

        // Set the LineData to the LineChart
        lineChart.setData(lineData);

        // Configure the chart
        configureChart();

        // Refresh the chart
        lineChart.invalidate();
    }

    private ArrayList<HashMap<String, String>> getLogEntries(int userId) {
        // Retrieve the data from the DBHelper
        DBHelper dbHelper = DBHelper.getInstance(this);
        return dbHelper.getLogEntries(userId);
    }

    private void configureChart() {
        // Set description
        lineChart.getDescription().setEnabled(false);

        // Set x-axis to the bottom
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Set y-axis to the left
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        // Set custom value formatter for Y-axis labels
        leftAxis.setValueFormatter(new CustomYAxisValueFormatter());

        // Disable the right y-axis
        lineChart.getAxisRight().setEnabled(false);
    }

    // Custom Y-axis value formatter class
    class CustomYAxisValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            // Customize the formatting of Y-axis labels here
            return String.valueOf((int) value); // Convert float to int for display
        }
    }
}
