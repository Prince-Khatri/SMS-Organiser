package com.smsorganiser.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.smsorganiser.R;
import com.smsorganiser.adapter.SMSAdapter;
import com.smsorganiser.manager.SMSManager;
import com.smsorganiser.model.SMSMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ChipGroup chipGroupCategory;
    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bnv = findViewById(R.id.navigationView);


        bnv.setSelectedItemId(R.id.nav_sms);
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
//        bnv.setSelectedItemId(R.id.nav_sms);

        SMSAdapter adpt = new SMSAdapter(new ArrayList<>());
        try {
            SMSManager mng = new SMSManager(this);
            RecyclerView recyclerView = findViewById(R.id.messagesScrollView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adpt);

            Executors.newSingleThreadExecutor().execute(() -> {
//                mng.loadAndSyncMessages();
                ArrayList<SMSMessage> msg = mng.refreshMessages();

                runOnUiThread(() -> {
                    adpt.updateData(msg);
                });


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        chipGroupCategory = findViewById(R.id.chipGroup);

        chipGroupCategory.setOnCheckedStateChangeListener((group, checkedId)->{
            Set<String> categories = new HashSet<>();
            for(int id:checkedId){
                Chip chip = findViewById(id);
                categories.add(chip.getText().toString().toLowerCase());
            }
            if(categories.contains("all")){
                categories.clear();
            }

            try{
                SMSManager manager = SMSManager.getInstance(this);
                manager.setFilter(categories);
                Executors.newSingleThreadExecutor().execute(()->{
                    ArrayList<SMSMessage> msg = manager.refreshMessages();
                    runOnUiThread(()->{
                        adpt.updateData(msg);
                    });

                manager.refreshMessages();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });



    }
}