package com.example.cee.restaurantmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ViewByDate extends AppCompatActivity {

    private static final String TAG = "ViewByDate";

    TextView display;
    Button searchDate;
    Button searchBtn;
    Button backBtn;
    DatePickerDialog.OnDateSetListener mDateSetListener;

    ListView displayList;
    Firebase mRef;
    ArrayList<String> bookingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_date);

        backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewByDate.this, ViewBooking.class);
                startActivity(myIntent);
            }
        });

        searchDate = (Button) findViewById((R.id.dateBtn));
        searchDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        ViewByDate.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        display = (TextView) findViewById(R.id.displayDate);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day + "/" + month + "/" + year);

                    String date = day + "/" + month + "/" + year;
                    display.setText(date);

            }
        };

        searchBtn = (Button) findViewById(R.id.search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListView listView = (ListView) findViewById(R.id.dateView);

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ViewByDate.this,
                        android.R.layout.simple_list_item_1,
                        bookingList);
                adapter.clear();
                listView.setAdapter(adapter);
                mRef = new Firebase("https://restaurantmanager-19e99.firebaseio.com/");

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Booking");

                displayList = (ListView) findViewById((R.id.dateView));
                ref.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {



                        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren() ) {
                            //HashMap<String, String> list = new HashMap<String, String>();
                            if (ds.child("Date").getValue().toString().equals(display.getText().toString())) {
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
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
