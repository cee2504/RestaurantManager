package com.example.cee.restaurantmanager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Calendar;
import java.util.Random;

public class AddBooking extends AppCompatActivity {

    private static final String TAG = "AddBooking";

    TextView display1;
    TextView display2;

    EditText firstN;
    EditText lastN;
    EditText phoneNum;
    EditText numberOfPeople;

    Spinner code;

    Button addBtn;
    Button backBtn;
    Button dateBtn;
    Button timeBtn;

    DatePickerDialog.OnDateSetListener mDateSetListener;
    TimePickerDialog.OnTimeSetListener mTimeSetListener;

    Firebase rootChild;

    Random rand = new Random();

    Calendar c2 = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_booking);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        code = (Spinner) findViewById((R.id.spinner));

        backBtn = (Button) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AddBooking.this, BookingManager.class);
                startActivity(myIntent);
            }
        });

        dateBtn = (Button) findViewById((R.id.date));
        dateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        display1 = (TextView) findViewById(R.id.textView4);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy: " + day + "/" + month + "/" + year);

                int currentDay = c2.get(Calendar.DAY_OF_MONTH);
                int currentMonth = c2.get(Calendar.MONTH) + 1;
                int currentYear = c2.get(Calendar.YEAR);

                if (((month > currentMonth)
                        || ((day >= currentDay) && (month == currentMonth)))
                        && (year == currentYear)
                        ) {
                    String date = day + "/" + month + "/" + year;
                    display1.setText(date);
                } else
                    display1.setText("Date invalid. Date cannot be before current date");
            }
        };


        timeBtn = (Button) findViewById(R.id.time);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(
                        AddBooking.this,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        mTimeSetListener,
                        hour, minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        display2 = (TextView) findViewById(R.id.textView5);
        mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if ((hour > 8) && (hour < 23)) {
                    String time = hour + ":" + minute;
                    display2.setText(time);
                } else
                    display2.setText("Time invalid. Must be between 9 and 23");
            }
        };

        rootChild = new Firebase("https://restaurantmanager-19e99.firebaseio.com/Booking");

        firstN = (EditText) findViewById(R.id.firstName);
        lastN = (EditText) findViewById(R.id.lastName);
        phoneNum = (EditText) findViewById(R.id.phone);
        numberOfPeople = (EditText) findViewById(R.id.num);

        addBtn = (Button) findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = firstN.getText().toString();
                String lastname = lastN.getText().toString();
                String phoneNumber = code.getSelectedItem().toString() + phoneNum.getText().toString();
                String numberPeople = numberOfPeople.getText().toString();
                String dateBooking = display1.getText().toString();
                String timeBooking = display2.getText().toString();

                if (firstname.matches("^[a-zA-Z]+$")
                        && lastname.matches("^[a-zA-Z]+$")
                        && phoneNum.getText().toString().matches("^[0-9]{7,8}$")
                        && !display1.getText().toString().equals("")
                        && !display2.getText().toString().equals("")
                        && !numberPeople.equals("")
                        ) {


                    int i = rand.nextInt(10000) + 1;
                    Firebase booking = rootChild.child("BookingID-" + i);

                    Firebase bookingChild = booking.child("First Name");
                    bookingChild.setValue(firstname);

                    bookingChild = booking.child("Last Name");
                    bookingChild.setValue(lastname);

                    bookingChild = booking.child("Phone Number");
                    bookingChild.setValue(phoneNumber);

                    bookingChild = booking.child("Date");
                    bookingChild.setValue(dateBooking);

                    bookingChild = booking.child("Time");
                    bookingChild.setValue(timeBooking);

                    bookingChild = booking.child("Number of People");
                    bookingChild.setValue(numberPeople);

                    addSuccess();
                } else if (!firstname.matches("^[a-zA-Z]+$")) {
                    nameError();
                } else if (!lastname.matches("^[a-zA-Z]+$")) {
                    nameError();
                } else if (!phoneNumber.matches("^[0-9]{9,10}$")) {
                    phoneError();
                } else if (display1.getText().toString().equals("")) {
                    dateError();
                } else if (display2.getText().toString().equals("")) {
                    timeError();
                } else if (numberPeople.equals("")) {
                    numError();
                }

            }
        });


    }

    public void addSuccess() {
        Toast.makeText(AddBooking.this, "New booking added!", Toast.LENGTH_SHORT).show();
    }

    public void nameError() {
        Toast.makeText(AddBooking.this, "Invalid Name. Character only!", Toast.LENGTH_SHORT).show();
    }

    public void phoneError() {
        Toast.makeText(AddBooking.this, "Invalid phone number. Must be 9-10 number total!", Toast.LENGTH_SHORT).show();
    }
    public void dateError() {
        Toast.makeText(AddBooking.this, "Date Invalid!", Toast.LENGTH_SHORT).show();
    }
    public void timeError() {
        Toast.makeText(AddBooking.this, "Time Invalid!", Toast.LENGTH_SHORT).show();
    }
    public void numError() {
        Toast.makeText(AddBooking.this, "Please enter number of people!", Toast.LENGTH_SHORT).show();
    }
}
