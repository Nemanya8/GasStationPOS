package com.incognito.gasstationpos.screens;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import com.incognito.gasstationpos.R;
import com.incognito.gasstationpos.models.GlobalData;
import com.incognito.gasstationpos.models.Item;
import com.incognito.gasstationpos.models.Receipt;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    private LinearLayout itemContainer;
    private TextView tvTotalCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        itemContainer = findViewById(R.id.itemContainer);
        tvTotalCost = findViewById(R.id.tvTotalCost);

        // Retrieve the Receipt object from the GlobalData singleton
        Receipt receipt = GlobalData.getInstance().getGlobalReceipt();

        if (receipt != null) {
            // Dynamically populate the item list using the receipt's items
            populateItems(receipt.getItems());

            // Set total cost from the receipt value (in RSD)
            tvTotalCost.setText("Ukupno: " + receipt.getValue() + " rsd");
        }
    }

    private void populateItems(List<Item> items) {
        // Clear the itemContainer to avoid adding duplicate items
        itemContainer.removeAllViews();

        for (Item item : items) {
            // Create a layout for each item
            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(8, 8, 8, 8);

            // Create Item Name & Quantity Layout
            LinearLayout itemInfoLayout = new LinearLayout(this);
            itemInfoLayout.setLayoutParams(new LayoutParams(
                    0, LayoutParams.WRAP_CONTENT, 2f));
            itemInfoLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView itemName = new TextView(this);
            itemName.setText(item.getName());
            itemName.setTextSize(20);  // Increased size
            itemName.setTextColor(getResources().getColor(android.R.color.black));

            TextView itemQuantity = new TextView(this);
            itemQuantity.setText("x" + item.getQuantity());
            itemQuantity.setTextSize(20);  // Increased size
            itemQuantity.setTextColor(getResources().getColor(android.R.color.black));
            itemQuantity.setPadding(8, 0, 0, 0);

            itemInfoLayout.addView(itemName);
            itemInfoLayout.addView(itemQuantity);

            // Create Item Cost
            TextView itemCost = new TextView(this);
            itemCost.setText((item.getValue() * item.getQuantity()) + " rsd");
            itemCost.setTextSize(20);  // Increased size
            itemCost.setTextColor(getResources().getColor(android.R.color.black));
            itemCost.setGravity(android.view.Gravity.END);
            itemCost.setLayoutParams(new LayoutParams(
                    0, LayoutParams.WRAP_CONTENT, 1f));

            itemLayout.addView(itemInfoLayout);
            itemLayout.addView(itemCost);

            // Add the item layout to the container
            itemContainer.addView(itemLayout);
        }
    }

}
