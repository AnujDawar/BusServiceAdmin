package com.example.anujdawar.busserviceadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> keys;
    Button addToServer;
    EditText busNumber, busType, busCompany, busFare, busRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://busservice3.firebaseio.com/user_details/");

        addToServer = (Button)findViewById(R.id.addToServerButtonID);
        busNumber = (EditText)findViewById(R.id.busNumberID);
        busCompany = (EditText)findViewById(R.id.busCompanyID);
        busType = (EditText)findViewById(R.id.busTypeID);
        busFare = (EditText)findViewById(R.id.busFareID);
        busRoute = (EditText)findViewById(R.id.busRouteID);

        keys = new ArrayList<>();

        Bundle b = getIntent().getExtras();
        keys = (ArrayList<String>) b.get("keys");

        addButtonListener();
    }

    void addButtonListener()
    {
        addToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.getParent().child("maintenance").setValue("1");

                myRef.getParent().child("BusRoutes/BusNumbers").child(busNumber.getText().toString()).setValue("0");
                myRef.getParent().child("BusRoutes/Company_Details").child(busNumber.getText().toString()).setValue(busCompany.getText().toString());
                myRef.getParent().child("BusRoutes/Type_Details").child(busNumber.getText().toString()).setValue(busType.getText().toString());
                myRef.getParent().child("BusRoutes/Fares_Details").child(busNumber.getText().toString()).setValue(busFare.getText().toString());
                myRef.getParent().child("BusRoutes/States_Details").child(busNumber.getText().toString()).setValue(busRoute.getText().toString());

                for(String s : keys)
                    myRef.child(s).setValue("1");

                myRef.getParent().child("maintenance").setValue("0");

                clearFields();
            }
        });
    }

    void clearFields()
    {
        busNumber.setText("");
        busType.setText("");
        busCompany.setText("");
        busFare.setText("");
        busRoute.setText("");
    }
}