package com.example.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the view that shows the numbers category
        ImageView numbers = (ImageView) findViewById(R.id.numbers);

        //Set click listener on the view
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@Link NumberActivity}
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);

                //Start the new activity
                startActivity(numbersIntent);
            }
        });

    //Find the view that shows the family category
    ImageView family = (ImageView) findViewById(R.id.family);

    //Set click listener on the view
        family.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Create a new intent to open the {@Link NumberActivity}
            Intent numbersIntent = new Intent(MainActivity.this, FamilyActivity.class);

            //Start the new activity
            startActivity(numbersIntent);
        }
    });
        //Find the view that shows the colors category
        ImageView colors = (ImageView) findViewById(R.id.colors);

        //Set click listener on the view
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@Link NumberActivity}
                Intent numbersIntent = new Intent(MainActivity.this, ColorsActivity.class);

                //Start the new activity
                startActivity(numbersIntent);
            }
        });
        //Find the view that shows the phrases category
        ImageView phrases = (ImageView) findViewById(R.id.phrases);

        //Set click listener on the view
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new intent to open the {@Link NumberActivity}
                Intent numbersIntent = new Intent(MainActivity.this, PhrasesActivity.class);

                //Start the new activity
                startActivity(numbersIntent);
            }
        });
}}

