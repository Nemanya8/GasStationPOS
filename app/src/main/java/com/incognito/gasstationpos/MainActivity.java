package com.incognito.gasstationpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnPlus, btnMinus, btnDalje;
    private TextView tvValue;
    private int currentValue = 500; // Start from 500 RSD

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDalje = findViewById(R.id.btnDalje);
        tvValue = findViewById(R.id.tvValue);

        // Set click listener for + button
        btnPlus.setOnClickListener(v -> {
            currentValue += 500; // Increment by 500
            tvValue.setText(currentValue + " RSD"); // Update TextView
        });

        // Set click listener for - button
        btnMinus.setOnClickListener(v -> {
            if (currentValue > 500) { // Avoid going below 500
                currentValue -= 500; // Decrement by 500
                tvValue.setText(currentValue + " RSD"); // Update TextView
            }
        });

        // Set click listener for Dalje button (Proceed)
        btnDalje.setOnClickListener(v -> {
            // Proceed to the next action, for example, open another activity
            Toast.makeText(this, "Proceeding with " + currentValue + " RSD", Toast.LENGTH_SHORT).show();
        });
    }
}
