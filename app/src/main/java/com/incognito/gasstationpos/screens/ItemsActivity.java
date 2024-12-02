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
    private ImageButton imgDrinks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        btnBack = findViewById(R.id.btnBack);
        btnNext = findViewById(R.id.btnNext);
        imgDrinks = findViewById(R.id.imgDrinks);

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
            }
        });

        imgDrinks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent drinksIntent = new Intent(ItemsActivity.this, DrinksActivity.class);
                startActivity(drinksIntent);
            }
        });
    }
}
