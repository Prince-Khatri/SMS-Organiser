package com.smsorganiser.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sms_table")
public class SMSMessage {
    @PrimaryKey()
    public long smsID;
    public long timeStamp;
    public String smsCategory, smsBody, smsAddress;
    public SMSMessage(){}
    public SMSMessage(long smsID, String smsBody, String smsAddress) {
        this.smsID = smsID;
        this.smsAddress = smsAddress;
        this.smsBody = smsBody;
    }
    public SMSMessage(long smsID, String smsBody, String smsAddress, long timeStamp) {
        this(smsID, smsBody, smsAddress);
        this.timeStamp = timeStamp;
    }


    public void setSmsCategory(String smsCategory) {
        this.smsCategory = smsCategory;
    }

    public String getSmsAddress() {
        return smsAddress;
    }

    public String getSmsBody() {
        return smsBody;
    }



    public String getSmsCategory() {
        return smsCategory;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}