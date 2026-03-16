package com.smsorganiser.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.smsorganiser.model.SMSMessage;
import com.smsorganiser.repository.SMSDao;

@Database(entities = {SMSMessage.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SMSDao smsDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "sms_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}