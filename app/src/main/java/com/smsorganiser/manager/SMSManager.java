package com.smsorganiser.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.smsorganiser.model.SMSMessage;
import com.smsorganiser.repository.SMSDao;
import com.smsorganiser.repository.SMSRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SMSManager {

    int maxRows=50;
    SMSRepository repo;

    public SMSManager(Context context){
        this.repo = new SMSRepository(context);
    }
public void readSMSAndSave(Context context){
    /*
    * @param context Android context for contextResolver
    * this function reads sms from device and save them in the sqlite table using repo->smsdao
    * */
    List<SMSMessage> listOfSMS = new ArrayList<>();
    Long last = repo.getLastSMSID();
    long lastReadSMSID = last==null?-1:last;

    Cursor cur = context.getContentResolver().query(
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

                listOfSMS.add(new SMSMessage(smsID, smsBody, smsAddress));
            }
            else break;
        }
        cur.close();

    }
    repo.saveListOfSMS(listOfSMS);

}

    public List<SMSMessage> getSMSMessages(List<String>categories){
        return repo.loadSMSWithCategories(categories, maxRows);
    }
    public List<SMSMessage> getAllSMSMessages(){


        return repo.loadAllSMS();
    }

}
