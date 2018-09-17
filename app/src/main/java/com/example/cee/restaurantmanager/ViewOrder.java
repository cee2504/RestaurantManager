package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewOrder extends AppCompatActivity {

    private static final String TAG = "ViewOrder";
    ListView displayList;
    Firebase mRef;
    ArrayList<String> orderList = new ArrayList<>();

    Button searchButton;
    Button deleteButton;
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        ListView listView = (ListView) findViewById(R.id.listOrder);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                orderList);
        listView.setAdapter(adapter);
        mRef = new Firebase("https://restaurantmanager-19e99.firebaseio.com/");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");

        displayList = (ListView) findViewById((R.id.listOrder));
        ref.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

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

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewOrder.this, OrderingManager.class);
                startActivity(myIntent);
            }
        });

        searchButton = (Button) findViewById(R.id.table);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewOrder.this, ViewByTable.class);
                startActivity(myIntent);
            }
        });

        deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewOrder.this, DeleteOrder.class);
                startActivity(myIntent);
            }
        });

    }
}
