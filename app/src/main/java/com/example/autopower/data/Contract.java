package com.example.autopower.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class Contract {

    private Contract(){

    }

    public static final String CONTENT_AUTHORITY="com.example.autopower";
    public static final Uri BASE_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String path = "roomInfo";



    public static class Table implements BaseColumns{


        public static final String T1_NAME="T1";
        public static final String T1_ID="_id";
        public static final String T1_DEVICE_NAME="RoomName";
        public static final String T1_IP_ADDR="IP";
        public static final String T1_DEVICES="Devices";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+path;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+path;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_URI,path);


    }
}
