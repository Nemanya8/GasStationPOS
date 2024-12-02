package com.incognito.gasstationpos.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.incognito.gasstationpos.R;
import com.incognito.gasstationpos.models.GlobalData;
import com.incognito.gasstationpos.models.Item;

public class CoffeeActivity extends AppCompatActivity {

    // Declare the views
    private ImageButton btnMinusCappuccino, btnPlusCappuccino;
    private TextView quantityCounterCappuccino;

    private ImageButton btnMinusEspresso, btnPlusEspresso;
    private TextView quantityCounterEspresso;

    private ImageButton btnMinusDomaca, btnPlusDomaca;
    private TextView quantityCounterDomaca;

    private ImageButton btnMinusCaj, btnPlusCaj;
    private TextView quantityCounterCaj;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee); // Make sure this is the correct layout file name

        // Initialize the views for Cappuccino
        btnMinusCappuccino = findViewById(R.id.btnMinusCappuccino);
        btnPlusCappuccino = findViewById(R.id.btnPlusCappuccino);
        quantityCounterCappuccino = findViewById(R.id.quantityCounterCappuccino);

// Initialize the views for Espresso
        btnMinusEspresso = findViewById(R.id.btnMinusEspresso);
        btnPlusEspresso = findViewById(R.id.btnPlusEspresso);
        quantityCounterEspresso = findViewById(R.id.quantityCounterEspresso);

// Initialize the views for Domaca
        btnMinusDomaca = findViewById(R.id.btnMinusDomaca);
        btnPlusDomaca = findViewById(R.id.btnPlusDomaca);
        quantityCounterDomaca = findViewById(R.id.quantityCounterDomaca);

        // Initialize the views for Caj
        btnMinusCaj = findViewById(R.id.btnMinusCaj);
        btnPlusCaj = findViewById(R.id.btnPlusCaj);
        quantityCounterCaj = findViewById(R.id.quantityCounterCaj);


        btnBack = findViewById(R.id.btnNext);

        // Set button click listeners to adjust the quantity
        btnMinusCappuccino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCappuccino, -1);
            }
        });

        btnPlusCappuccino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCappuccino, 1);
            }
        });

        btnMinusEspresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterEspresso, -1);
            }
        });

        btnPlusEspresso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterEspresso, 1);
            }
        });

        btnMinusDomaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterDomaca, -1);
            }
        });

        btnPlusDomaca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterDomaca, 1);
            }
        });

        btnMinusCaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCaj, -1);
            }
        });

        btnPlusCaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCaj, 1);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCart();
                finish();
            }
        });
    }

    private void updateQuantity(TextView counterTextView, int delta) {
        int currentQuantity = Integer.parseInt(counterTextView.getText().toString());
        int newQuantity = currentQuantity + delta;
        if (newQuantity >= 0) {
            counterTextView.setText(String.valueOf(newQuantity));
        }
    }

    public void updateCart() {
        String[] drinkNames = {"Cappuccino", "Espresso", "Domaca", "Caj"};
        double[] prices = {130, 350, 140, 120};
        TextView[] quantityCounters = {quantityCounterCappuccino, quantityCounterEspresso, quantityCounterDomaca, quantityCounterCaj};

        for (int i = 0; i < drinkNames.length; i++) {
            int quantity = Integer.parseInt(quantityCounters[i].getText().toString());

            if (quantity > 0) {
                String name = drinkNames[i];
                String lowercaseName = name.toLowerCase().replace(" ", "_");
                double price = prices[i];

                Item item = new Item(name, lowercaseName, price, quantity, "pice");
                GlobalData.getInstance().getGlobalReceipt().addItem(item);

                GlobalData.getInstance().getGlobalReceipt().addValue(item.getValue() * item.getQuantity());
            }
        }
    }


}
