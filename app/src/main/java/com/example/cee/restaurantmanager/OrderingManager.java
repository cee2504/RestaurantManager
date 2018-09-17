package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderingManager extends AppCompatActivity {

    Button backBtn;
    Button placeOrderButton;
    Button viewOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_manager);

        backBtn = (Button) findViewById(R.id.back2);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OrderingManager.this, MainPage.class);
                startActivity(myIntent);
            }
        });

        placeOrderButton = (Button) findViewById(R.id.placeOrder);
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OrderingManager.this, PlaceOrder.class);
                startActivity(myIntent);
            }
        });

        viewOrderButton = (Button) findViewById(R.id.viewOrder);
        viewOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(OrderingManager.this, ViewOrder.class);
                startActivity(myIntent);
            }
        });
    }
}
