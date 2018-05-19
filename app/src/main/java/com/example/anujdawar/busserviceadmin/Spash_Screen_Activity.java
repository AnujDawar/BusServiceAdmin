package com.example.anujdawar.busserviceadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Spash_Screen_Activity extends AppCompatActivity {

    DatabaseReference myRef;
    FirebaseDatabase database;
    ArrayList<String> keys;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash__screen_);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReferenceFromUrl("https://busservice3.firebaseio.com/user_details/");
        keys = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for(DataSnapshot s : dataSnapshot.getChildren())
                    keys.add(s.getKey());


                new Handler().postDelayed(new Runnable() {
                     @Override
                     public void run()
                     {
                         Intent myIntent = new Intent(Spash_Screen_Activity.this, MainActivity.class);
                         myIntent.putExtra("keys", keys);
                         startActivity(myIntent);
                         finish();
                     }
                 }, 0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {   }
        });
    }
}