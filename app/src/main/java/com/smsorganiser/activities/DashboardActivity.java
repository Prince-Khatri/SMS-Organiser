package com.smsorganiser.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smsorganiser.R;
import com.smsorganiser.manager.SMSManager;
import com.smsorganiser.model.CategoryCount;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    SMSManager mng;
    BarChart barChart;
    List<BarEntry> barChartValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bnv = findViewById(R.id.navigationView);
        barChart = findViewById(R.id.barChart);
        try {
            mng = SMSManager.getInstance(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        bnv.setSelectedItemId(R.id.nav_stats);
        bnv.setOnItemSelectedListener(e->{
            if(e.getItemId()==R.id.nav_home) {
                startActivity(new Intent(this, SetupActivity.class));
                return true;
            }
            else if(e.getItemId()==R.id.nav_sms) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            }
            else if(e.getItemId()==R.id.nav_stats){
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            }
            return false;
        });
        loadDashboardChart();

    }
    private void loadDashboardChart() {

        Executors.newSingleThreadExecutor().execute(() -> {

            ArrayList<CategoryCount> categories = mng.getDashboardStats();

            ArrayList<String> labels = new ArrayList<>();
            ArrayList<BarEntry> values = new ArrayList<>();

            int i = 0;
            for (CategoryCount cc : categories) {
                values.add(new BarEntry(i, cc.getNoOfSMS()));
                labels.add(cc.getCategoryName());
                i++;
            }

            runOnUiThread(() -> updateChart(values, labels));
        });
    }
    private void updateChart(List<BarEntry> values, List<String> labels) {

        BarDataSet dataSet = new BarDataSet(values, "SMS Categories");
        dataSet.setColor(Color.parseColor("#3F51B5"));
        dataSet.setValueTextColor(Color.GRAY);
        dataSet.setValueTextSize(12f);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

        // X Axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setTextColor(Color.GRAY);
        xAxis.setDrawGridLines(false);

        // Y Axis
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.GRAY);
        leftAxis.setGridColor(Color.LTGRAY);

        barChart.getAxisRight().setEnabled(false);

        barChart.setExtraBottomOffset(50f);
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);

        barChart.setFitBars(true);
        barChart.animateY(1200);

        barChart.invalidate();
    }
}