package com.example.autopower;

import android.content.ContentUris;
import android.content.Loader;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.autopower.data.Contract;

public class CatalogRoom extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    CatalogRoomAdapter catalogRoomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CatalogRoom.this,RoomDetails.class);

                startActivity(intent);
            }
        });

        catalogRoomAdapter = new CatalogRoomAdapter(this,null);

        ListView listView = findViewById(R.id.list_room);

        listView.setAdapter(catalogRoomAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(CatalogRoom.this,RoomDetails.class);
                intent.setData(ContentUris.withAppendedId(Contract.Table.CONTENT_URI,id));
                startActivity(intent);

            }
        });

        getLoaderManager().initLoader(0,null,this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = new String[]{Contract.Table.T1_ID, Contract.Table.T1_DEVICE_NAME};

        return new CursorLoader(this, Contract.Table.CONTENT_URI,projection,null
                ,null,null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        catalogRoomAdapter.swapCursor(cursor);

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        catalogRoomAdapter.swapCursor(null);

    }
}
