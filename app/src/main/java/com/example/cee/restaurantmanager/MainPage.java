package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {

    Button bookBtn;
    Button ordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        bookBtn = (Button) findViewById(R.id.book);
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainPage.this, BookingManager.class);
                startActivity(myIntent);
            }
        });

        ordBtn = (Button) findViewById(R.id.order);
        ordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainPage.this, OrderingManager.class);
                startActivity(myIntent);
            }
        });
    }
}
