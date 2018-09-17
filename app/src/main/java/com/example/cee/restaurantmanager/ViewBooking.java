package com.example.cee.restaurantmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewBooking extends AppCompatActivity {

    private static final String TAG = "ViewBooking";
    ListView displayList;
    Firebase mRef;
    ArrayList<String> bookingList = new ArrayList<>();

    Button back;
    Button dateButton;
    Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewBooking.this, BookingManager.class);
                startActivity(myIntent);
            }
        });

        dateButton = (Button) findViewById(R.id.dateBtn);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewBooking.this, ViewByDate.class);
                startActivity(myIntent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listBooking);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                bookingList);
        listView.setAdapter(adapter);
        mRef = new Firebase("https://restaurantmanager-19e99.firebaseio.com/");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Booking");

        displayList = (ListView) findViewById((R.id.listBooking));
        ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

                    adapter.add(ds.getKey());
                    adapter.add(ds.child("Date").getKey().toString() + ": " + ds.child("Date").getValue().toString());
                    adapter.add(ds.child("First Name").getKey().toString() + ": " + ds.child("First Name").getValue().toString());
                    adapter.add(ds.child("Last Name").getKey().toString() + ": " + ds.child("Last Name").getValue().toString());
                    adapter.add(ds.child("Number of People").getKey().toString() + ": " + ds.child("Number of People").getValue().toString());
                    adapter.add(ds.child("Phone Number").getKey().toString() + ": " + ds.child("Phone Number").getValue().toString());
                    adapter.add(ds.child("Time").getKey().toString() + ": " + ds.child("Time").getValue().toString());
                    adapter.add("******************************************************");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        deleteButton = (Button) findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewBooking.this, DeleteBooking.class);
                startActivity(myIntent);
            }
        });
    }
}

