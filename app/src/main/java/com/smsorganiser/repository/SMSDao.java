package com.smsorganiser.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.smsorganiser.model.SMSMessage;

import java.util. List;
@Dao
public interface SMSDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertSMS(SMSMessage sms);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkSMS( List<SMSMessage> listOfSMS);
    @Query("Select * From sms_table")
     List<SMSMessage> getAllSMS();
    @Query("Select * From sms_table Limit :maxRows")
     List<SMSMessage> getAllSMSWithLimit(int maxRows);

    @Query("SELECT * FROM sms_table WHERE smsCategory IN (:categories) LIMIT :maxRows")
     List<SMSMessage> getSMSByCategory( List<String> categories, int maxRows);

    @Query("Update sms_table set smsCategory=:category where smsID in (:ids)")
    void updateCategory( List<Integer> ids, String category);

    @Query("SELECT COALESCE(MAX(smsID),-1) from sms_table")
    long getLastSMSID();


}
