package com.smsorganiser.activities;

import static android.Manifest.permission.READ_SMS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smsorganiser.R;
import com.smsorganiser.manager.SMSManager;
import com.smsorganiser.manager.SetupManager;

import java.util.concurrent.Executors;

public class SetupActivity extends AppCompatActivity {
    BottomNavigationView bnv;
    SetupManager setupManagerInstance;
    SMSManager smsManagerInstance;
    TextView setupMessageTv;
    Button setupButton;
    CheckBox termsAndConditionsCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_app_setup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bnv = findViewById(R.id.navigationView);
        bnv.setSelectedItemId(R.id.nav_setup);
        bnv.setOnItemSelectedListener(e->{
            if(e.getItemId()==R.id.nav_setup) {
//                startActivity(new Intent(this, SetupActivity.class));
                return true;
            }
            else if(e.getItemId()==R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            }
            else if(e.getItemId()==R.id.nav_dash){
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            }
            return false;
        });

        try {
            setupManagerInstance = SetupManager.getInstance(this);
            if (setupManagerInstance.isSetupDone()) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return;
            }
            smsManagerInstance = SMSManager.getInstance(this);
            initialiseView();
            setupButton.setOnClickListener(e->{
                setupListener();
            });

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setupListener() {
        if(!termsAndConditionsCheckbox.isChecked()){
            setupMessageTv.setText("Please accept Terms & Conditions");
            return;
        }
        if(!setupManagerInstance.haveSMSPermission()){

            if(shouldShowRequestPermissionRationale(READ_SMS)){
                setupMessageTv.setText("We Need SMS permission to categorize your messages.");
            }

            requestPermissions(
                    new String[]{READ_SMS},
                    100
            );
            return;
        }
        setupButton.setEnabled(false);
        setupMessageTv.setText("Please Wait, Setup Inprogress...");

        Executors.newSingleThreadExecutor().execute(()->{
            setupManagerInstance.runInitialSetup();
            runOnUiThread(()->{
                setupMessageTv.setText("✔ Setup Complete");
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        });


    }

    void initialiseView(){
        setupMessageTv = findViewById(R.id.setupMessage);
        setupButton = findViewById(R.id.setupButton);
        termsAndConditionsCheckbox= findViewById(R.id.termsAndConditionsCheckbox);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults.length>0 &&
                    grantResults[0] == PERMISSION_GRANTED)
            {
                setupButton.performClick();
            }
            else{
                if(!shouldShowRequestPermissionRationale(READ_SMS)){
                    setupMessageTv.setText("Permission permanently denied. Enable it form settings.");
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(android.net.Uri.parse("package:"+getPackageName()));
                    startActivity(intent);
                }
                else {
                    setupMessageTv.setText("SMS permission required");
                }
            }
        }


    }

    @Override
    protected void onResume(){
        super.onResume();
        boolean setupDone = setupManagerInstance !=null && setupManagerInstance.isSetupDone();
        boolean hasPermission = checkSelfPermission(READ_SMS) != PERMISSION_GRANTED;
        if(setupDone && hasPermission){
            setupManagerInstance.clearSetupFlag();
            recreate();
        }
    }


}