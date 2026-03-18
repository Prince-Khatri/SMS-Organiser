package com.smsorganiser.manager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.smsorganiser.classifier.SMSClassifier;
import com.smsorganiser.classifier.SMSInferenceService;
import com.smsorganiser.model.CategoryCount;
import com.smsorganiser.model.SMSMessage;
import com.smsorganiser.repository.SMSDao;
import com.smsorganiser.repository.SMSRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ai.onnxruntime.OrtException;

public class SMSManager {

    /*
    * Made class singletone
    * */

//    int maxRows=50;
    SMSRepository repo;
    SMSInferenceService smsInferenceService;
    Context context;
    private static String prefFileName = "app_prefs";

    SharedPreferences pref;

    public static SMSManager instance;


    public SMSManager(Context context) throws Exception{
        this.context = context;
        this.repo = new SMSRepository(context);
        this.smsInferenceService = SMSInferenceService.getInstance(context);
        pref = context.getSharedPreferences(prefFileName, MODE_PRIVATE);
    }

    public static synchronized SMSManager getInstance(Context context) throws Exception {
        if(instance==null){
            instance = new SMSManager(context);
        }
        return instance;
    }
    public void readSMSAndSave(){
    /*
    * @param context Android context for contextResolver
    * this function reads sms from device and save them in the sqlite table using repo->smsdao
    * */
    ArrayList<SMSMessage> listOfSMS = new ArrayList<>();
    Long last = repo.getLastSMSID();
    long lastReadSMSID = last==null?-1:last;

    Cursor cur = this.context.getContentResolver().query(
            Uri.parse("content://sms/inbox"),
            null,
            null,
            null,
            "_id DESC"
    );

    if(cur!=null){

        while(cur.moveToNext()){
            long smsID = cur.getLong(cur.getColumnIndexOrThrow("_id"));
            if(smsID>lastReadSMSID) {

                String smsBody = cur.getString(cur.getColumnIndexOrThrow("body"));
                String smsAddress = cur.getString(cur.getColumnIndexOrThrow("address"));
                long smsTimeStamp = cur.getLong(cur.getColumnIndexOrThrow("date"));

                listOfSMS.add(new SMSMessage(smsID, smsBody, smsAddress, smsTimeStamp));
            }
            else break;
        }
        cur.close();

    }
    repo.saveListOfSMS(listOfSMS);

}

    public ArrayList<SMSMessage> getSMSMessages(){
        ArrayList<String> categories = this.getFilter();
        if(categories.isEmpty()) return this.repo.loadAllSMS();
        return repo.loadSMSWithCategories(categories);
    }
    public ArrayList<SMSMessage> refreshMessages(){
        return getSMSMessages();
    }
    public void syncSMS(){
        /*
        * Don't run  without creating thread.
        * */
        try{
            ArrayList<SMSMessage> listOfSMS = this.getSMSMessages();
            SMSInferenceService smsInferenceService = SMSInferenceService.getInstance(context);
            smsInferenceService.classifySMS(listOfSMS);
            repo.saveListOfSMS(listOfSMS);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void setFilter(Set<String> categories){
        Set<String> set = new HashSet<>(categories);
        SharedPreferences.Editor editor = pref.edit();
        editor.putStringSet("filters",set);
        editor.apply();
    }

    public void loadAndSyncMessages(){
        this.readSMSAndSave();
        this.syncSMS();
    }

    public ArrayList<String> getFilter(){
        Set<String> set = pref.getStringSet("filters", new HashSet<>());
        return new ArrayList<>(set);
    }

    public ArrayList<CategoryCount> getDashboardStats(){
        return repo.getCategoryCounts();
    }


}
