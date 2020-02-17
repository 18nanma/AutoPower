package com.example.autopower;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.autopower.data.Contract;

public class CatalogRoomAdapter extends CursorAdapter {

    public CatalogRoomAdapter(Context context, Cursor c) {
        super(context, c);
    }





    @Override
    public View newView(Context context, final Cursor cursor, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.room_list,viewGroup,false);


        return v;
    }




    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView title = (TextView) view.findViewById(R.id.textView);

        int titleIndex = cursor.getColumnIndex(Contract.Table.T1_DEVICE_NAME);



        int id = cursor.getColumnIndex(Contract.Table.T1_ID);
        final long id_copy = cursor.getLong(id);



        String string = cursor.getString(titleIndex);

        title.setText(string);


    }
}