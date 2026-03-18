package com.smsorganiser.manager;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import java.util.concurrent.Executors;

public class SetupManager {
    private static SetupManager instance;
    private Context context;
    private SharedPreferences pref;
    private static String prefFileName = "app_prefs";
    private static String pref_status = "setup_status";

    SetupManager(Context context){
        this.context = context.getApplicationContext();
        this.pref = context.getSharedPreferences(prefFileName, MODE_PRIVATE);
    }
    public static synchronized SetupManager getInstance(Context context){
        if(instance==null) instance = new SetupManager(context);
        return instance;
    }

    public boolean isSetupDone(){
        return pref.getBoolean(pref_status,false);
    }

    public void markSetupDone(){
        this.pref.edit().putBoolean(pref_status, true).apply();
    }

    public void clearSetupFlag() {
        this.pref.edit().putBoolean(pref_status, false).apply();
    }

    public boolean haveSMSPermission(){
        return ContextCompat.checkSelfPermission(
                this.context,
                Manifest.permission.READ_SMS
        ) == PackageManager.PERMISSION_GRANTED;
    }
    public void runInitialSetup(){
        try {
            SMSManager smsManagerInstance = SMSManager.getInstance(this.context);
            smsManagerInstance.loadAndSyncMessages();
            markSetupDone();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
