package com.incognito.gasstationpos;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.incognito.gasstationpos.models.Item;
import com.incognito.gasstationpos.models.Receipt;
import com.incognito.gasstationpos.services.FirestoreService;
import com.incognito.gasstationpos.services.PosService;

import java.math.BigDecimal;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirestoreService firestoreService;
    private PosService posService;
    private Button btnAddItem, btnShowAllItems, btnAddReceipt, btnPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize FirestoreService
        firestoreService = new FirestoreService();

        // Intialize PosService
        String packageName = getPackageName();
        Context appContext = getApplicationContext();
        posService = new PosService(packageName, appContext);

        // Initialize buttons
        btnAddItem = findViewById(R.id.btnAddItem);
        btnShowAllItems = findViewById(R.id.btnShowAllItems);
        btnAddReceipt = findViewById(R.id.btnAddReceipt);
        btnPay = findViewById(R.id.btnPay);

        // Add Item button
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        // Show All Items button
        btnShowAllItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAllItems();
            }
        });

        // Add Receipt button
        btnAddReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReceipt();
            }
        });

        // Pay button
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
    }

    // Method to add an Item
    private void addItem() {
        Item newItem = new Item("Test Item", "item_image.png", 9.99, 10);
        firestoreService.addItem(newItem);

        Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
    }

    // Method to show all items
    private void showAllItems() {
        firestoreService.getAllItems(new FirestoreService.FirestoreCallback<List<Item>>() {
            @Override
            public void onSuccess(List<Item> result) {
                // For now, we'll just log the result
                for (Item item : result) {
                    Log.d("MainActivity", "Item: " + item.getName() + ", Value: " + item.getValue());
                }
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("MainActivity", "Error fetching items", e);
                Toast.makeText(MainActivity.this, "Error fetching items", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to add a Receipt
    private void addReceipt() {
        // Creating an example receipt
        Receipt newReceipt = new Receipt( 100.0, System.currentTimeMillis());

        // Create an item and add it to the receipt's items list
        Item item = new Item("Test Item", "item_image.png", 9.99, 10);
        newReceipt.getItems().add(item); // This should work now

        firestoreService.addReceipt(newReceipt);

        Toast.makeText(this, "Receipt added", Toast.LENGTH_SHORT).show();
    }

   private void pay() {
        posService.Pay(new BigDecimal(4761));
       try {
           Thread.sleep(30000); // Sleep for 10 seconds (10000 milliseconds)
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       posService.PrintReceipt();
    }



}
