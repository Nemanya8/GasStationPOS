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

public class CigarsActivity extends AppCompatActivity {

    // Declare the views
    private ImageButton btnMinusWinston, btnPlusWinston;
    private TextView quantityCounterWinston;

    private ImageButton btnMinusMalboro, btnPlusMalboro;
    private TextView quantityCounterMalboro;

    private ImageButton btnMinusLM, btnPlusLM;
    private TextView quantityCounterLM;

    private ImageButton btnMinusSobranie, btnPlusSobranie;
    private TextView quantityCounterSobranie;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cigarettes); // Make sure this is the correct layout file name

        // Initialize the views for Winston
        btnMinusWinston = findViewById(R.id.btnMinusWinston);
        btnPlusWinston = findViewById(R.id.btnPlusWinston);
        quantityCounterWinston = findViewById(R.id.quantityCounterWinston);

// Initialize the views for Malboro
        btnMinusMalboro = findViewById(R.id.btnMinusMalboro);
        btnPlusMalboro = findViewById(R.id.btnPlusMalboro);
        quantityCounterMalboro = findViewById(R.id.quantityCounterMalboro);

// Initialize the views for LM
        btnMinusLM = findViewById(R.id.btnMinusLM);
        btnPlusLM = findViewById(R.id.btnPlusLM);
        quantityCounterLM = findViewById(R.id.quantityCounterLM);

        // Initialize the views for Sobranie
        btnMinusSobranie = findViewById(R.id.btnMinusSobranie);
        btnPlusSobranie = findViewById(R.id.btnPlusSobranie);
        quantityCounterSobranie = findViewById(R.id.quantityCounterSobranie);


        btnBack = findViewById(R.id.btnNext);

        // Set button click listeners to adjust the quantity
        btnMinusWinston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterWinston, -1);
            }
        });

        btnPlusWinston.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterWinston, 1);
            }
        });

        btnMinusMalboro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterMalboro, -1);
            }
        });

        btnPlusMalboro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterMalboro, 1);
            }
        });

        btnMinusLM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterLM, -1);
            }
        });

        btnPlusLM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterLM, 1);
            }
        });

        btnMinusSobranie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterSobranie, -1);
            }
        });

        btnPlusSobranie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterSobranie, 1);
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
        String[] drinkNames = {"Winston", "Malboro", "LM", "Sobranie"};
        double[] prices = {300, 350, 280, 500};
        TextView[] quantityCounters = {quantityCounterWinston, quantityCounterMalboro, quantityCounterLM, quantityCounterSobranie};

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
