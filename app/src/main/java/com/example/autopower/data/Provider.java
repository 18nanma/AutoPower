package com.example.autopower.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class Provider extends ContentProvider {

    public static final int ROOM = 100;
    public static final int ROOM_ID=101;


    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.path,ROOM);
        sUriMatcher.addURI(Contract.CONTENT_AUTHORITY,Contract.path+"/#",ROOM_ID);


    }
    private DataBase dataBase;
    @Override
    public boolean onCreate() {

        dataBase = new DataBase(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final int match = sUriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = dataBase.getReadableDatabase();
        Cursor c;
        switch(match){
            case ROOM:
                c=sqLiteDatabase.query(Contract.Table.T1_NAME,projection,selection,selectionArgs,null,null,null);
                break;

            case ROOM_ID:
                selection = Contract.Table.T1_ID +"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                c=sqLiteDatabase.query(Contract.Table.T1_NAME,projection,selection,selectionArgs,null,null,null);
                break;

            default:
                throw new IllegalArgumentException("Couldn't query for the uri:"+uri);


        }
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }






    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {


        final int match = sUriMatcher.match(uri);

        switch (match){
            case ROOM:
                return addRoom(uri,contentValues);


            default:
                throw new IllegalArgumentException("Error insertion");

        }

    }



    private Uri addRoom(Uri uri,ContentValues contentValues){
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        String deviceName = contentValues.getAsString(Contract.Table.T1_DEVICE_NAME);
        String IP = contentValues.getAsString(Contract.Table.T1_IP_ADDR);

        if(deviceName == null){
            throw new IllegalArgumentException("Invalid Name");

        }
        if(IP==null){
            throw new IllegalArgumentException("Invalid IP address");
        }

        long id = sqLiteDatabase.insert(Contract.Table.T1_NAME,null,contentValues);

        if (id == -1) {
            Log.e("Provider", "Failed to insert row for " + uri);
            return null;
        }


        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);



    }



    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match){
            case ROOM:
                return UpdateRoom(uri,contentValues,selection,selectionArgs);
            case ROOM_ID:
                selection = Contract.Table.T1_ID+"=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return UpdateRoom(uri,contentValues,selection,selectionArgs);



            default:
                throw new IllegalArgumentException("Couldn't Update pet");
        }

    }

    private int UpdateRoom(Uri uri,ContentValues contentValues,String selection,String[] selectionArgs){
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        int rowsUpdated= sqLiteDatabase.update(Contract.Table.T1_NAME,contentValues,selection,
                selectionArgs);

        if(rowsUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);

        switch (match){
            case ROOM_ID:
                selection = Contract.Table.T1_ID+"=?";
                selectionArgs=new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = sqLiteDatabase.delete(Contract.Table.T1_NAME,selection,selectionArgs);
                break;



            default:
                throw new IllegalArgumentException("Deletion not supported for "+uri);

        }

        if(rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri) {

        final int match = sUriMatcher.match(uri);
        switch (match){
            case ROOM:
                return Contract.Table.CONTENT_LIST_TYPE;
            case ROOM_ID:
                return Contract.Table.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalStateException("Unknown uri "+uri+"with match"+match);
        }


    }



}