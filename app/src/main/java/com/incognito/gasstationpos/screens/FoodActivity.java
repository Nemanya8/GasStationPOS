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

public class FoodActivity extends AppCompatActivity {

    // Declare the views
    private ImageButton btnMinusChipsy, btnPlusChipsy;
    private TextView quantityCounterChipsy;

    private ImageButton btnMinusSmoki, btnPlusSmoki;
    private TextView quantityCounterSmoki;

    private ImageButton btnMinusTwix, btnPlusTwix;
    private TextView quantityCounterTwix;

    private ImageButton btnMinusKikiriki, btnPlusKikiriki;
    private TextView quantityCounterKikiriki;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food); // Make sure this is the correct layout file name

        // Initialize the views for Chipsy
        btnMinusChipsy = findViewById(R.id.btnMinusChipsy);
        btnPlusChipsy = findViewById(R.id.btnPlusChipsy);
        quantityCounterChipsy = findViewById(R.id.quantityCounterChipsy);

// Initialize the views for Smoki
        btnMinusSmoki = findViewById(R.id.btnMinusSmoki);
        btnPlusSmoki = findViewById(R.id.btnPlusSmoki);
        quantityCounterSmoki = findViewById(R.id.quantityCounterSmoki);

// Initialize the views for Twix
        btnMinusTwix = findViewById(R.id.btnMinusTwix);
        btnPlusTwix = findViewById(R.id.btnPlusTwix);
        quantityCounterTwix = findViewById(R.id.quantityCounterTwix);

        // Initialize the views for Kikiriki
        btnMinusKikiriki = findViewById(R.id.btnMinusKikiriki);
        btnPlusKikiriki = findViewById(R.id.btnPlusKikiriki);
        quantityCounterKikiriki = findViewById(R.id.quantityCounterKikiriki);


        btnBack = findViewById(R.id.btnNext);

        // Set button click listeners to adjust the quantity
        btnMinusChipsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterChipsy, -1);
            }
        });

        btnPlusChipsy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterChipsy, 1);
            }
        });

        btnMinusSmoki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterSmoki, -1);
            }
        });

        btnPlusSmoki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterSmoki, 1);
            }
        });

        btnMinusTwix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterTwix, -1);
            }
        });

        btnPlusTwix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterTwix, 1);
            }
        });

        btnMinusKikiriki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterKikiriki, -1);
            }
        });

        btnPlusKikiriki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(quantityCounterKikiriki, 1);
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
        String[] drinkNames = {"Chipsy", "Smoki", "Twix", "Kikiriki"};
        double[] prices = {130, 350, 140, 120};
        TextView[] quantityCounters = {quantityCounterChipsy, quantityCounterSmoki, quantityCounterTwix, quantityCounterKikiriki};

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
