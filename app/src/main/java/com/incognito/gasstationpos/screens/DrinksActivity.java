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

public class DrinksActivity extends AppCompatActivity {

    // Declare the views
    private ImageButton btnMinusCocaCola, btnPlusCocaCola;
    private TextView quantityCounterCocaCola;

    private ImageButton btnMinusRedBull, btnPlusRedBull;
    private TextView quantityCounterRedBull;

    private ImageButton btnMinusPepsi, btnPlusPepsi;
    private TextView quantityCounterPepsi;

    private ImageButton btnMinusSprite, btnPlusSprite;
    private TextView quantityCounterSprite;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks); // Make sure this is the correct layout file name

        // Initialize the views for CocaCola
        btnMinusCocaCola = findViewById(R.id.btnMinusCocaCola);
        btnPlusCocaCola = findViewById(R.id.btnPlusCocaCola);
        quantityCounterCocaCola = findViewById(R.id.quantityCounterCocaCola);

// Initialize the views for RedBull
        btnMinusRedBull = findViewById(R.id.btnMinusRedBull);
        btnPlusRedBull = findViewById(R.id.btnPlusRedBull);
        quantityCounterRedBull = findViewById(R.id.quantityCounterRedBull);

// Initialize the views for Pepsi
        btnMinusPepsi = findViewById(R.id.btnMinusPepsi);
        btnPlusPepsi = findViewById(R.id.btnPlusPepsi);
        quantityCounterPepsi = findViewById(R.id.quantityCounterPepsi);

        // Initialize the views for Sprite
        btnMinusSprite = findViewById(R.id.btnMinusSprite);
        btnPlusSprite = findViewById(R.id.btnPlusSprite);
        quantityCounterSprite = findViewById(R.id.quantityCounterSprite);


        btnBack = findViewById(R.id.btnNext);

        // Set button click listeners to adjust the quantity
        btnMinusCocaCola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCocaCola, -1);
            }
        });

        btnPlusCocaCola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterCocaCola, 1);
            }
        });

        btnMinusRedBull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterRedBull, -1);
            }
        });

        btnPlusRedBull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterRedBull, 1);
            }
        });

        btnMinusPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterPepsi, -1);
            }
        });

        btnPlusPepsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterPepsi, 1);
            }
        });

        btnMinusSprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterSprite, -1);
            }
        });

        btnPlusSprite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterSprite, 1);
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
        String[] drinkNames = {"CocaCola", "RedBull", "Pepsi", "Sprite"};
        double[] prices = {130, 350, 140, 120};
        TextView[] quantityCounters = {quantityCounterCocaCola, quantityCounterRedBull, quantityCounterPepsi, quantityCounterSprite};

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
