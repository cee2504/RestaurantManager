package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BookingManager extends AppCompatActivity {

    Button backBtn;
    Button addBtn;
    Button viewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_manager);

        backBtn = (Button) findViewById(R.id.back1);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BookingManager.this, MainPage.class);
                startActivity(myIntent);
            }
        });

        addBtn = (Button) findViewById(R.id.addBooking);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BookingManager.this, AddBooking.class);
                startActivity(myIntent);
            }
        });

        viewBtn = (Button) findViewById(R.id.viewBooking);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(BookingManager.this, ViewBooking.class);
                startActivity(myIntent);
            }
        });
    }
}
