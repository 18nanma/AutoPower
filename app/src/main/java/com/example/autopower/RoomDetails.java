package com.example.autopower;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.autopower.data.Contract;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomDetails extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Button add;
    private static final int LOADER_ROOM = 0;
    private static long id;
    private Button save;
    private Uri u;
    String[] list;
    ArrayList<String> arrayList;
    ListView listView;
    private EditText mName;
    private EditText mIPAdrr;
    private EditText mDeviceName;
    customAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_details);

        Intent intent = getIntent();
        u = intent.getData();

        add = findViewById(R.id.addDevice);
        save = findViewById(R.id.saveDetails);

        listView = findViewById(R.id._dynamic);
        arrayList =new ArrayList<String>();
        mName = findViewById(R.id.room_name);
        mIPAdrr = findViewById(R.id.ip_address);
        mDeviceName = findViewById(R.id.d_name);
        arrayAdapter = new customAdapter(this,arrayList,u,listView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mName.getText().toString().isEmpty()){
                    //Toast.makeText(this,R.string.title_cant_empty,Toast.LENGTH_SHORT).show();

                    Snackbar.make(findViewById(android.R.id.content),"Room name can't be empty",Snackbar.LENGTH_SHORT).show();

                }

                if(arrayList.size() == 0) {
                    //Toast.makeText(this, "No participants gn", Toast.LENGTH_SHORT).show();
                    Snackbar.make(findViewById(android.R.id.content),"Add atleast 1 device",Snackbar.LENGTH_SHORT).show();
                }

                else if (arrayList.size() != 0 || list != null ) {

                    saveData();
                    finish();
                }
            }
        });

        if(u==null){
            setTitle("Add Room");

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String device_name = mDeviceName.getText().toString();

                    if(device_name.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Device name",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //participants.add(participant_name);
                        if(arrayList.contains(device_name)){
                            Snackbar.make(findViewById(android.R.id.content),"Already added",Snackbar.LENGTH_SHORT).show();

                        }
                        else{

                            arrayList.add(device_name);
                            listView.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                            getListViewSize(listView);
                            mDeviceName.setText("");

                        }


                    }


                }
            });
        }
        else{

            getLoaderManager().initLoader(LOADER_ROOM,null,this);

            id = ContentUris.parseId(u);

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String participant_name = mDeviceName.getText().toString();
                    if(participant_name.isEmpty()){
                        Toast.makeText(getApplicationContext(),"Enter device name",Toast.LENGTH_SHORT).show();
                    }
                    else if (arrayList.contains(participant_name)){

                        Snackbar.make(findViewById(android.R.id.content),"Device with same name already exists",Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        try{
                            arrayList.add(arrayAdapter.getCount(),participant_name);
                            listView.setAdapter(arrayAdapter);
                            arrayAdapter.notifyDataSetChanged();
                            getListViewSize(listView);
                            mDeviceName.setText("");

                        }
                        catch (IndexOutOfBoundsException e){

                        }
                    }

                }
            });


        }

    }

    public void saveData(){


        String parti="";


        for (int i = 0; i < arrayList.size(); ++i) {
            parti += "," + arrayList.get(i);
        }

        ContentValues contentValues = new ContentValues();
        String name = mName.getText().toString().trim();
        String IP = mIPAdrr.getText().toString();


        contentValues.put(Contract.Table.T1_DEVICE_NAME, name);
        contentValues.put(Contract.Table.T1_IP_ADDR, IP);
        contentValues.put(Contract.Table.T1_DEVICES, parti);



        if (u == null) {
            Uri identifier = getContentResolver().insert(Contract.Table.CONTENT_URI, contentValues);


            if (identifier == null) {
                Toast.makeText(this, getString(R.string.failed_to_add_room), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.room_created), Toast.LENGTH_SHORT).show();
            }

        } else {

            int rowsAffected=getContentResolver().update(u, contentValues, null, null);

            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this,"Update failed",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this,"Updated",
                        Toast.LENGTH_SHORT).show();
            }
        }




    }


    public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        //Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{Contract.Table.T1_ID,
                Contract.Table.T1_DEVICE_NAME,
                Contract.Table.T1_IP_ADDR, Contract.Table.T1_DEVICES};

        return new CursorLoader(this,
                u,
                projection,
                null,
                null,
                null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null) {
            return;
        }

        if (data.moveToFirst()) {

            int title = data.getColumnIndex(Contract.Table.T1_DEVICE_NAME);
            int desc = data.getColumnIndex(Contract.Table.T1_IP_ADDR);
            int devices = data.getColumnIndex(Contract.Table.T1_DEVICES);


            mName.setText(data.getString(title));
            mIPAdrr.setText(data.getString(desc));
            list = data.getString(devices).split(",");
            arrayList = new ArrayList<>(Arrays.asList(list));

            arrayList.remove(0);

            arrayAdapter = new customAdapter(this,arrayList,u,listView);
            listView.setAdapter(arrayAdapter);
            getListViewSize(listView);


        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mName.setText("");
        mIPAdrr.setText("");

    }
}


