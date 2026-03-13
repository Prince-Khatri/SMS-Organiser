package com.smsorganiser.repository;

import android.content.Context;

import com.smsorganiser.db.AppDatabase;
import com.smsorganiser.model.SMSMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class SMSRepository {
    private SMSDao smsDao;

    public SMSRepository(Context context){
        AppDatabase db = AppDatabase.getDatabase(context);
        this.smsDao = db.smsDao();
    }



    public ArrayList<SMSMessage> loadAllSMS(){

        return (ArrayList<SMSMessage>) smsDao.getAllSMS();
    }

    public ArrayList<SMSMessage> loadAllSMS(int maxRows){
        return (ArrayList<SMSMessage>) smsDao.getAllSMSWithLimit(maxRows);
    }

    public ArrayList<SMSMessage> loadSMSWithCategories(ArrayList<String> categories, int maxRows){
        return (ArrayList<SMSMessage>) smsDao.getSMSByCategory(categories, maxRows);
    }

    public long getLastSMSID(){
        return smsDao.getLastSMSID();
    }


    public void saveListOfSMS(ArrayList<SMSMessage> listOfSMS) {
            smsDao.insertBulkSMS(listOfSMS);
    }
}
