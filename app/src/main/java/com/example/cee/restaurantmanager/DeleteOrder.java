package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteOrder extends AppCompatActivity {

    Button back;
    Button search;
    Button deleteButton;

    TextView display;

    Firebase mRef;
    ListView displayList;
    ArrayList<String> orderID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_order);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(DeleteOrder.this, ViewOrder.class);
                startActivity(myIntent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.orderListview);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(DeleteOrder.this,
                android.R.layout.simple_list_item_1,
                orderID);

        listView.setAdapter(adapter);
        mRef = new Firebase("https://restaurantmanager-19e99.firebaseio.com/");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Order");

        display = (EditText) findViewById(R.id.deleteId);

        search = (Button) findViewById(R.id.searchId);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clear();

                displayList = (ListView) findViewById((R.id.dateView));
                ref.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String getID = "OrderID-" + display.getText().toString();
                        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren()) {

                            if (ds.getKey().toString().equals(getID)) {
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

        deleteButton = (Button) findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        String getID = "OrderID-" + display.getText().toString();
                        for (com.google.firebase.database.DataSnapshot ds : dataSnapshot.getChildren() ) {

                            if (ds.getKey().toString().equals(getID)) {
                                ds.getRef().setValue(null);
                                adapter.clear();
                                Toast.makeText(DeleteOrder.this, "Order Deleted!", Toast.LENGTH_SHORT).show();
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
