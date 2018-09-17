package com.example.cee.restaurantmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Random;

public class PlaceOrder extends AppCompatActivity {

    Spinner tableNum;

    EditText food1;
    EditText food2;
    EditText food3;
    EditText food4;
    EditText food5;

    Button placeOrderBtn;
    Button backButton;

    CheckBox menu1;
    CheckBox menu2;
    CheckBox menu3;
    CheckBox menu4;
    CheckBox menu5;


    Firebase rootChild;

    Random rand = new Random();

    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Spinner spinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(PlaceOrder.this, R.array.tables, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        backButton = (Button) findViewById(R.id.backBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PlaceOrder.this, OrderingManager.class);
                startActivity(myIntent);
            }
        });

        tableNum = (Spinner) findViewById((R.id.spinner2));

        menu1 = (CheckBox) findViewById(R.id.checkBox1);
        menu2 = (CheckBox) findViewById(R.id.checkBox2);
        menu3 = (CheckBox) findViewById(R.id.checkBox3);
        menu4 = (CheckBox) findViewById(R.id.checkBox4);
        menu5 = (CheckBox) findViewById(R.id.checkBox5);

        food1 = (EditText) findViewById(R.id.quantity1);
        food2 = (EditText) findViewById(R.id.quantity2);
        food3 = (EditText) findViewById(R.id.quantity3);
        food4 = (EditText) findViewById(R.id.quantity4);
        food5 = (EditText) findViewById(R.id.quantity5);


        rootChild = new Firebase("https://restaurantmanager-19e99.firebaseio.com/Order");

        placeOrderBtn = (Button) findViewById(R.id.placeOrder);
        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int price = 0;
                int i = 0;
                i = rand.nextInt(10000) + 1;

                String table = tableNum.getSelectedItem().toString();
                String order1 = food1.getText().toString();
                String order2 = food2.getText().toString();
                String order3 = food3.getText().toString();
                String order4 = food4.getText().toString();
                String order5 = food5.getText().toString();

                if ((menu1.isChecked() && !order1.equals("0")) ||
                        (menu2.isChecked() && !order2.equals("0")) ||
                        (menu3.isChecked() && !order3.equals("0")) ||
                        (menu4.isChecked() && !order4.equals("0")) ||
                        (menu5.isChecked() && !order5.equals("0"))) {

                    Firebase order = rootChild.child("OrderID-" + i);
                    Firebase orderChildTable = order.child("Table");
                    orderChildTable.setValue(table);

                    if (menu1.isChecked() && !order1.equals("0")) {
                        Firebase orderChild = order.child("Fried Chicken");
                        orderChild.setValue(order1);
                        price = price + 20 * Integer.parseInt(order1);
                        orderChild = order.child("Total Price");
                        orderChild.setValue("$" + String.valueOf(price));
                    }
                    if (menu2.isChecked() && !order2.equals("0")) {
                        Firebase orderChild = order.child("Steak");
                        orderChild.setValue(order2);
                        price = price + 25 * Integer.parseInt(order2);
                        orderChild = order.child("Total Price");
                        orderChild.setValue("$" + String.valueOf(price));
                    }
                    if (menu3.isChecked() && !order3.equals("0")) {
                        Firebase orderChild = order.child("Grilled Fish");
                        orderChild.setValue(order3);
                        price = price + 15 * Integer.parseInt(order3);
                        orderChild = order.child("Total Price");
                        orderChild.setValue("$" + String.valueOf(price));
                    }
                    if (menu4.isChecked() && !order4.equals("0")) {
                        Firebase orderChild = order.child("Soup");
                        orderChild.setValue(order4);
                        price = price + 10 * Integer.parseInt(order4);
                        orderChild = order.child("Total Price");
                        orderChild.setValue("$" + String.valueOf(price));
                    }
                    if (menu5.isChecked() && !order5.equals("0")) {
                        Firebase orderChild = order.child("Bacon and Egg");
                        orderChild.setValue(order5);
                        price = price + 5 * Integer.parseInt(order5);
                        orderChild = order.child("Total Price");
                        orderChild.setValue("$" + String.valueOf(price));
                    }

                    Toast.makeText(PlaceOrder.this, "New order added!", Toast.LENGTH_SHORT).show();

                } else
                    Toast.makeText(PlaceOrder.this, "Please select at least one dish!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
