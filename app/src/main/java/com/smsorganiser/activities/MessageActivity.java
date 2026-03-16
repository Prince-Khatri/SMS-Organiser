package com.smsorganiser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.smsorganiser.R;

public class MessageActivity extends AppCompatActivity {
    TextView senderName, smsBody;
    Chip category;
    ImageView senderImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_message);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Connect to views
        Intent intent = getIntent();
        senderName = findViewById(R.id.smsSenderName);
        smsBody = findViewById(R.id.smsMessageBody);
        category = findViewById(R.id.smsCategory);


        String sender = intent.getStringExtra("senderName");
        String messageBody = intent.getStringExtra("messageBody");
        String smsCategory = intent.getStringExtra("smsCategory");

        senderName.setText(sender);
        smsBody.setText(messageBody);
        category.setText(smsCategory);

        BottomNavigationView bnv = findViewById(R.id.navigationView);
        bnv.setSelectedItemId(R.id.nav_sms);

    }
}