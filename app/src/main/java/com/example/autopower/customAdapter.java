package com.example.autopower;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.autopower.data.Contract;
import com.example.autopower.data.DataBase;
import java.util.ArrayList;
import java.util.ArrayList;

public class customAdapter<C> extends ArrayAdapter<String> {
    ArrayList<String> arrayList;
    long id;
    Uri uri;
    ListView listView;
    DataBase db;
    Cursor cursor;
    int count;
    public customAdapter(@NonNull Context context, ArrayList<String> arrayList, Uri uri, ListView listView) {
        super(context, 0,arrayList);
        this.arrayList=arrayList;
        this.uri=uri;
        this.listView=listView;


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =convertView;



        if(view==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.custom_layout,parent,false);

        }
        TextView textView = (TextView) view.findViewById(R.id.p1_name);
        ImageButton del = (ImageButton) view.findViewById(R.id.x);

        String p_name = getItem(position);


        try {
            id =ContentUris.parseId(uri);
        }
        catch (NullPointerException e){

        }

        textView.setText(p_name);
        try{
            db = new DataBase(getContext());
            SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
            String query = "SELECT * FROM "+ Contract.Table.T1_NAME;
            cursor = sqLiteDatabase.rawQuery(query,null);
            count= cursor.getCount();

        }
        catch (Exception e){

        }
        finally {
            db.close();
            cursor.close();
        }




        if(count==0) {



            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String toDel = (String) getItem(position);

                    arrayList.remove(toDel);
                    remove(toDel);
                    notifyDataSetChanged();
                    RoomDetails.getListViewSize(listView);


                }
            });


        }
        else {

            del.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_close_disabled));
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"Can't delete",Toast.LENGTH_SHORT).show();
                }
            });

        }


        return view;
    }


}
