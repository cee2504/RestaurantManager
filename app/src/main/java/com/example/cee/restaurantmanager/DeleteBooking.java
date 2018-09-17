package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteBooking extends AppCompatActivity {

    Button back;
    Button search;
    Button deleteButton;

    TextView display;

    Firebase mRef;
    ListView displayList;
    ArrayList<String> bookingID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_booking);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DeleteBooking.this, ViewBooking.class);
                startActivity(myIntent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.dateView);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteBooking.this,
                android.R.layout.simple_list_item_1,
                bookingID);

        listView.setAdapter(adapter);
        mRef = new Firebase("https://restaurantmanager-19e99.firebaseio.com/");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Booking");

        display = (TextView) findViewById(R.id.deleteId);

        search = (Button) findViewById(R.id.searchID);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();

                displayList = (ListView) findViewById((R.id.dateView));
                ref.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String getID = "BookingID-" + display.getText().toString();
                        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren() ) {
                            //HashMap<String, String> list = new HashMap<String, String>();
                            if (ds.getKey().toString().equals(getID)) {
                                adapter.add(ds.getKey());
                                adapter.add(ds.child("Date").getKey().toString() + ": " + ds.child("Date").getValue().toString());
                                adapter.add(ds.child("First Name").getKey().toString() + ": " + ds.child("First Name").getValue().toString());
                                adapter.add(ds.child("Last Name").getKey().toString() + ": " + ds.child("Last Name").getValue().toString());
                                adapter.add(ds.child("Number of People").getKey().toString() + ": " + ds.child("Number of People").getValue().toString());
                                adapter.add(ds.child("Phone Number").getKey().toString() + ": " + ds.child("Phone Number").getValue().toString());
                                adapter.add(ds.child("Time").getKey().toString() + ": " + ds.child("Time").getValue().toString());
                            }

                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        deleteButton = (Button) findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String getID = "BookingID-" + display.getText().toString();
                        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren() ) {
                            //HashMap<String, String> list = new HashMap<String, String>();
                            if (ds.getKey().toString().equals(getID)) {
                                ds.getRef().setValue(null);
                                adapter.clear();
                                Toast.makeText(DeleteBooking.this, "Booking Deleted!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
