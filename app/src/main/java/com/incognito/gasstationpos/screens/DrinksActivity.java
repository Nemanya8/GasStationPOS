package com.incognito.gasstationpos.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.incognito.gasstationpos.R;
import com.incognito.gasstationpos.models.GlobalData;
import com.incognito.gasstationpos.models.Item;

public class DrinksActivity extends AppCompatActivity {

    // Declare the views
    private Button btnMinusCocaCola, btnPlusCocaCola;
    private TextView quantityCounterCocaCola;

    private Button btnMinusRedBull, btnPlusRedBull;
    private TextView quantityCounterRedBull;

    private Button btnMinusPepsi, btnPlusPepsi;
    private TextView quantityCounterPepsi;

    private Button btnMinusSprite, btnPlusSprite;
    private TextView quantityCounterSprite;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks); // Make sure this is the correct layout file name

        // Initialize the views
        btnMinusCocaCola = findViewById(R.id.btnMinusCocaCola);
        btnPlusCocaCola = findViewById(R.id.btnPlusCocaCola);
        quantityCounterCocaCola = findViewById(R.id.quantityCounterCocaCola);

        btnMinusRedBull = findViewById(R.id.btnMinusRedBull);
        btnPlusRedBull = findViewById(R.id.btnPlusRedBull);
        quantityCounterRedBull = findViewById(R.id.quantityCounterRedBull);

        btnMinusPepsi = findViewById(R.id.btnMinusPepsi);
        btnPlusPepsi = findViewById(R.id.btnPlusPepsi);
        quantityCounterPepsi = findViewById(R.id.quantityCounterPepsi);

        btnMinusSprite = findViewById(R.id.btnMinusSprite);
        btnPlusSprite = findViewById(R.id.btnPlusSprite);
        quantityCounterSprite = findViewById(R.id.quantityCounterSprite);

        btnBack = findViewById(R.id.btnBack);

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

        // Set up the back button to go back to the previous screen
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCart();
                finish(); // Close the activity and go back to the previous screen
            }
        });
    }

    // Helper function to update quantity
    private void updateQuantity(TextView counterTextView, int delta) {
        int currentQuantity = Integer.parseInt(counterTextView.getText().toString());
        int newQuantity = currentQuantity + delta;
        if (newQuantity >= 0) {
            counterTextView.setText(String.valueOf(newQuantity));
        }
    }

    public void updateCart() {
        String[] drinkNames = {"CocaCola", "RedBull", "Pepsi", "Sprite"};
        double[] prices = {130, 150, 120, 120}; // Example prices per liter for each drink
        TextView[] quantityCounters = {quantityCounterCocaCola, quantityCounterRedBull, quantityCounterPepsi, quantityCounterSprite};

        for (int i = 0; i < drinkNames.length; i++) {
            int quantity = Integer.parseInt(quantityCounters[i].getText().toString());

            if (quantity > 0) {
                String name = drinkNames[i];
                String lowercaseName = name.toLowerCase().replace(" ", "_"); // Convert name to lowercase and replace spaces with '_'
                double price = prices[i];
                int qty = quantity;

                Item item = new Item(name, lowercaseName, price, qty, "pice"); // Category is always "pice"
                GlobalData.getInstance().getGlobalReceipt().addItem(item);

                GlobalData.getInstance().getGlobalReceipt().addValue(item.getValue() * item.getQuantity());
            }
        }
    }


}
