package com.example.autopower;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_naviagtion);

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.info);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.metrics:
                        startActivity(new Intent(getApplicationContext(),
                                Metrics.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),
                                CatalogRoom.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.info:
                        return true;
                }
                return false;
            }
        });
    }
}
