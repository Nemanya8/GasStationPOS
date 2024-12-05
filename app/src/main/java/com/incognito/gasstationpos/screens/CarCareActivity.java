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

public class CarCareActivity extends AppCompatActivity {

    // Declare the views
    private ImageButton btnMinusCarOil, btnPlusCarOil;
    private TextView quantityCounterCarOil;

    private ImageButton btnMinusBrisaci, btnPlusBrisaci;
    private TextView quantityCounterBrisaci;

    private ImageButton btnMinusG12, btnPlusG12;
    private TextView quantityCounterG12;

    private ImageButton btnMinusAdBlue, btnPlusAdBlue;
    private TextView quantityCounterAdBlue;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carcare); // Make sure this is the correct layout file name

        // Initialize the views for CarOil
        btnMinusCarOil = findViewById(R.id.btnMinusCarOil);
        btnPlusCarOil = findViewById(R.id.btnPlusCarOil);
        quantityCounterCarOil = findViewById(R.id.quantityCounterCarOil);

// Initialize the views for Brisaci
        btnMinusBrisaci = findViewById(R.id.btnMinusBrisaci);
        btnPlusBrisaci = findViewById(R.id.btnPlusBrisaci);
        quantityCounterBrisaci = findViewById(R.id.quantityCounterBrisaci);

// Initialize the views for G12
        btnMinusG12 = findViewById(R.id.btnMinusG12);
        btnPlusG12 = findViewById(R.id.btnPlusG12);
        quantityCounterG12 = findViewById(R.id.quantityCounterG12);

        // Initialize the views for AdBlue
        btnMinusAdBlue = findViewById(R.id.btnMinusAdBlue);
        btnPlusAdBlue = findViewById(R.id.btnPlusAdBlue);
        quantityCounterAdBlue = findViewById(R.id.quantityCounterAdBlue);


        btnBack = findViewById(R.id.btnNext);

        // Set button click listeners to adjust the quantity
        btnMinusCarOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCarOil, -1);
            }
        });

        btnPlusCarOil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCarOil, 1);
            }
        });

        btnMinusBrisaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterBrisaci, -1);
            }
        });

        btnPlusBrisaci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterBrisaci, 1);
            }
        });

        btnMinusG12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterG12, -1);
            }
        });

        btnPlusG12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterG12, 1);
            }
        });

        btnMinusAdBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterAdBlue, -1);
            }
        });

        btnPlusAdBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterAdBlue, 1);
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
        String[] drinkNames = {"CarOil", "Brisaci", "G12", "AdBlue"};
        double[] prices = {840, 100, 930, 1400};
        TextView[] quantityCounters = {quantityCounterCarOil, quantityCounterBrisaci, quantityCounterG12, quantityCounterAdBlue};

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
