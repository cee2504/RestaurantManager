package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewByTable extends AppCompatActivity {

    Spinner viewTable;
    Button searchButton;
    Button backButton;

    ListView displayList;

    Firebase mRef;
    ArrayList<String> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_by_table);

        backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewByTable.this, ViewOrder.class);
                startActivity(myIntent);
            }
        });

        Spinner spinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapterTable = ArrayAdapter.createFromResource(ViewByTable.this, R.array.tables, android.R.layout.simple_spinner_item);
        adapterTable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterTable);

        ListView listView = (ListView) findViewById(R.id.listOrder);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                orderList);
        listView.setAdapter(adapter);
        mRef = new Firebase("https://restaurantmanager-19e99.firebaseio.com/");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");

        displayList = (ListView) findViewById((R.id.listOrder));

        viewTable = (Spinner) findViewById(R.id.spinner3);

        searchButton = (Button) findViewById(R.id.selectBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

                            if (ds.child("Table").getValue().toString().equals(viewTable.getSelectedItem().toString())) {
                                adapter.add(ds.getKey());
                                adapter.add(ds.child("Table").getKey().toString() + ": " + ds.child("Table").getValue().toString());


                                if (ds.hasChild("Bacon and Egg")) {
                                    adapter.add(ds.child("Bacon and Egg").getKey().toString() + ": " + ds.child("Bacon and Egg").getValue().toString());
                                }

                                if (ds.hasChild("Fried Chicken")) {
                                    adapter.add(ds.child("Fried Chicken").getKey().toString() + ": " + ds.child("Fried Chicken").getValue().toString());
                                }

                                if (ds.hasChild("Grilled Fish")) {
                                    adapter.add(ds.child("Grilled Fish").getKey().toString() + ": " + ds.child("Grilled Fish").getValue().toString());
                                }

                                if (ds.hasChild("Soup")) {
                                    adapter.add(ds.child("Soup").getKey().toString() + ": " + ds.child("Soup").getValue().toString());
                                }

                                if (ds.hasChild("Steak")) {
                                    adapter.add(ds.child("Steak").getKey().toString() + ": " + ds.child("Steak").getValue().toString());
                                }

                                if (ds.hasChild("Table")) {
                                    adapter.add(ds.child("Table").getKey().toString() + ": " + ds.child("Table").getValue().toString());
                                }

                                if (ds.hasChild("Total Price")) {
                                    adapter.add(ds.child("Total Price").getKey().toString() + ": " + ds.child("Total Price").getValue().toString());
                                }

                                adapter.add("****************************************************");

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
