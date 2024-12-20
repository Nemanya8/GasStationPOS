package com.incognito.gasstationpos.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.incognito.gasstationpos.R;

public class ItemsActivity extends AppCompatActivity {

    private Button btnBack, btnNext;
    private ImageButton imgDrinks, imgFood, imgCoffee, imgCigars, imgCarCare;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        imgDrinks = findViewById(R.id.imgDrinks);
        imgFood = findViewById(R.id.imgSnacks);
        imgCoffee = findViewById(R.id.imgCoffee);
        imgCigars = findViewById(R.id.imgCigarettes);
        imgCarCare = findViewById(R.id.imgCarCare);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkoutIntent = new Intent(ItemsActivity.this, CheckoutActivity.class);
                startActivity(checkoutIntent);
                finish();
            }
        });

        imgDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drinksIntent = new Intent(ItemsActivity.this, DrinksActivity.class);
                startActivity(drinksIntent);
            }
        });

        imgFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent foodIntent = new Intent(ItemsActivity.this, FoodActivity.class);
                startActivity(foodIntent);
            }
        });

        imgCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coffeeIntent = new Intent(ItemsActivity.this, CoffeeActivity.class);
                startActivity(coffeeIntent);
            }
        });

        imgCigars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cigarsIntent = new Intent(ItemsActivity.this, CigarsActivity.class);
                startActivity(cigarsIntent);
            }
        });

        imgCarCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent carcareIntent = new Intent(ItemsActivity.this, CarCareActivity.class);
                startActivity(carcareIntent);
            }
        });
    }
}
