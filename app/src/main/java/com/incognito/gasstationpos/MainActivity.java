package com.incognito.gasstationpos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.incognito.gasstationpos.models.Item;
import com.incognito.gasstationpos.models.Receipt;
import com.incognito.gasstationpos.services.ItemService;
import com.incognito.gasstationpos.services.ReceiptService;
import com.incognito.gasstationpos.screens.SelectedFuelActivity;

import java.util.List;
public class MainActivity extends AppCompatActivity {

    private ImageButton btnImageButtonBmb95, btnImageButtonBmb100, btnImageButtonDizel, btnImageButtonDizelPremium;
    private Button btnAddNewItem;  // Declare it here but do not initialize it outside onCreate()
    private ItemService itemService;
    private ReceiptService receiptService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ImageButtons
        btnImageButtonBmb95 = findViewById(R.id.btnImageButtonBmb95);
        btnImageButtonBmb100 = findViewById(R.id.btnImageButtonBmb100);
        btnImageButtonDizel = findViewById(R.id.btnImageButtonDizel);
        btnImageButtonDizelPremium = findViewById(R.id.btnImageButtonDizelPremium);

        // Initialize services
        String itemsFilePath = getFilesDir() + "/data/items.csv"; // Internal storage path
        String receiptFilePath = getFilesDir() + "/data/receipts.csv";
        itemService = new ItemService(itemsFilePath);
        receiptService = new ReceiptService(receiptFilePath);

        // Initialize the 'Add New Item' button inside onCreate
        btnAddNewItem = findViewById(R.id.btnAddNewItem);
        btnAddNewItem.setOnClickListener(v -> addNewItem());

        // Set click listeners for the ImageButtons
        btnImageButtonBmb95.setOnClickListener(v -> navigateToSelectedFuel("BMB95"));
        btnImageButtonBmb100.setOnClickListener(v -> navigateToSelectedFuel("BMB100"));
        btnImageButtonDizel.setOnClickListener(v -> navigateToSelectedFuel("Dizel"));
        btnImageButtonDizelPremium.setOnClickListener(v -> navigateToSelectedFuel("DizelPremium"));

        // Load items and receipts asynchronously to prevent blocking the UI
        loadItemsAndReceipts();
    }

    private void navigateToSelectedFuel(String fuelType) {
        Intent intent = new Intent(MainActivity.this, SelectedFuelActivity.class);
        intent.putExtra("FUEL_TYPE", fuelType);  // Pass the selected fuel type to the next activity
        startActivity(intent);
    }

    private void loadItemsAndReceipts() {
        // Load items from ItemService
        List<Item> items = itemService.loadItemsFromCSV();  // Adjust the path as needed
        if (items != null && !items.isEmpty()) {
            for (Item item : items) {
                // Handle loaded items (e.g., display or use them)
                Toast.makeText(this, "Item loaded: " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No items found", Toast.LENGTH_SHORT).show();
        }

        // Load receipts from ReceiptService
        List<Receipt> receipts = receiptService.loadReceiptsFromCSV();  // Adjust the path as needed
        if (receipts != null && !receipts.isEmpty()) {
            for (Receipt receipt : receipts) {
                // Handle loaded receipts (e.g., display or use them)
                Toast.makeText(this, "Receipt loaded with value: " + receipt.getValue(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No receipts found", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewItem() {
        // Simulirani podaci iz CSV fajla
        Item[] items = new Item[] {
                new Item("Coca Cola", "cola", 150, 50, "pice"),
                new Item("Chips", "chipsy", 200, 30, "hrana"),
                new Item("Winston", "winston", 350, 20, "cigare"),
                new Item("Twix", "twix", 120, 40, "hrana"),
                new Item("Monster Energy", "monster", 250, 25, "pice")
        };

        // Dodavanje svakog artikla koristeći servis
        for (Item item : items) {
            itemService.createItem(item);
            Toast.makeText(this, "Added item: " + item.getName(), Toast.LENGTH_SHORT).show();
        }
        logAllItemsFromCSV();
    }


    private void logAllItemsFromCSV() {
        // Load items from CSV
        List<Item> items = itemService.loadItemsFromCSV();

        // Log all items to Logcat
        for (Item item : items) {
            Log.d("ItemLog", "Item: " + item.getId() + ", Name: " + item.getName() + ", Value: " + item.getValue() + ", Quantity: " + item.getQuantity() + ", Category: " + item.getCategory());
        }


    }

}
