package com.incognito.gasstationpos.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.incognito.gasstationpos.R;

public class SelectedFuelActivity extends AppCompatActivity {

    private EditText edtLiters;
    private TextView txtPrice;
    private String fuelType;
    private double pricePerLiter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_fuel);

        ImageView imgSelectedFuel = findViewById(R.id.imgSelectedFuel);
        Button btnBack = findViewById(R.id.btnBack);
        Button btnNext = findViewById(R.id.btnNext);
        Button btnPlus = findViewById(R.id.btnPlus);
        Button btnMinus = findViewById(R.id.btnMinus);
        edtLiters = findViewById(R.id.edtLiters);
        txtPrice = findViewById(R.id.txtPrice);

        Intent intent = getIntent();
        fuelType = intent.getStringExtra("FUEL_TYPE");

        switch (fuelType) {
            case "BMB95":
                imgSelectedFuel.setImageResource(R.drawable.bmb95);
                pricePerLiter = 145;
                break;
            case "BMB100":
                imgSelectedFuel.setImageResource(R.drawable.bmb100);
                pricePerLiter = 155;
                break;
            case "Dizel":
                imgSelectedFuel.setImageResource(R.drawable.dizel);
                pricePerLiter = 160;
                break;
            case "DizelPremium":
                imgSelectedFuel.setImageResource(R.drawable.dizel_premium);
                pricePerLiter = 170;
                break;
        }

        edtLiters.setText("0 L");
        btnBack.setOnClickListener(v -> onBackPressed());
        btnNext.setOnClickListener(v -> {
            Intent nextIntent = new Intent(SelectedFuelActivity.this, ItemsActivity.class);
            startActivity(nextIntent);
        });

        btnPlus.setOnClickListener(v -> {
            int liters = Integer.parseInt(edtLiters.getText().toString().replace(" L", ""));
            liters++;
            edtLiters.setText(liters + " L");
            updatePrice();
            btnNext.setEnabled(liters > 0);
        });

        btnMinus.setOnClickListener(v -> {
            int liters = Integer.parseInt(edtLiters.getText().toString().replace(" L", ""));
            if (liters > 0) {
                liters--;
                edtLiters.setText(liters + " L");
                updatePrice();
                btnNext.setEnabled(liters > 0);
            } else {
                Toast.makeText(this, "Cannot have less than 0 liters", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updatePrice() {
        int liters = Integer.parseInt(edtLiters.getText().toString().replace(" L", ""));
        double totalPrice = liters * pricePerLiter;
        txtPrice.setText("Cena: " + totalPrice + " RSD");
    }
}

