package com.incognito.gasstationpos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import com.incognito.gasstationpos.screens.SelectedFuelActivity;


public class MainActivity extends AppCompatActivity {

    private ImageButton btnImageButtonBmb95, btnImageButtonBmb100, btnImageButtonDizel, btnImageButtonDizelPremium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnImageButtonBmb95 = findViewById(R.id.btnImageButtonBmb95);
        btnImageButtonBmb100 = findViewById(R.id.btnImageButtonBmb100);
        btnImageButtonDizel = findViewById(R.id.btnImageButtonDizel);
        btnImageButtonDizelPremium = findViewById(R.id.btnImageButtonDizelPremium);

        btnImageButtonBmb95.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSelectedFuel("BMB95");
            }
        });

        btnImageButtonBmb100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSelectedFuel("BMB100");
            }
        });

        btnImageButtonDizel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSelectedFuel("Dizel");
            }
        });

        btnImageButtonDizelPremium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSelectedFuel("Dizel Premium");
            }
        });
    }

    private void navigateToSelectedFuel(String fuelType) {
        var instance = GlobalData.getInstance();
        Intent intent = new Intent(MainActivity.this, SelectedFuelActivity.class);
        intent.putExtra("FUEL_TYPE", fuelType);
        startActivity(intent);
    }
}
