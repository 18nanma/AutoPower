package com.example.autopower.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

    public static final String DB_NAME="Rooms.db";
    public static final int DB_VERSION = 1;


    public DataBase(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String T1 = "CREATE TABLE "+Contract.Table.T1_NAME+"("+Contract.Table.T1_ID
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ Contract.Table.T1_DEVICE_NAME+" TEXT,"+ Contract.Table.T1_IP_ADDR
                +" TEXT,"+ Contract.Table.T1_DEVICES+" TEXT);";

        db.execSQL(T1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}





