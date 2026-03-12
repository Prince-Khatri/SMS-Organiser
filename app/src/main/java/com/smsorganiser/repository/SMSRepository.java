package com.smsorganiser.repository;

import android.content.Context;

import com.smsorganiser.db.AppDatabase;
import com.smsorganiser.model.SMSMessage;

import java.util.List;
import java.util.concurrent.Executors;

public class SMSRepository {
    private SMSDao smsDao;

    public SMSRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        this.smsDao = db.smsDao();
    }



    public List<SMSMessage> loadAllSMS(){

        return smsDao.getAllSMS();
    }

    public List<SMSMessage> loadAllSMS(int maxRows){
        return smsDao.getAllSMSWithLimit(maxRows);
    }

    public List<SMSMessage> loadSMSWithCategories(List<String> categories, int maxRows){
        return smsDao.getSMSByCategory(categories, maxRows);
    }

    public long getLastSMSID(){
        return smsDao.getLastSMSID();
    }


    public void saveListOfSMS(List<SMSMessage> listOfSMS) {
        Executors.newSingleThreadExecutor().execute( ()->{
            smsDao.insertBulkSMS(listOfSMS);
        });
    }
}
