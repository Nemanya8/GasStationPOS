package com.incognito.gasstationpos.services;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.incognito.gasstationpos.models.Item;
import com.incognito.gasstationpos.models.Receipt;

import java.util.ArrayList;
import java.util.List;

public class FirestoreService {

    private final FirebaseFirestore db;

    public FirestoreService() {
        db = FirebaseFirestore.getInstance();
    }

    // -------------------- CRUD for Receipts --------------------

    // Create: Add a new receipt
    public void addReceipt(Receipt receipt) {
        if (receipt == null) {
            Log.e("FirestoreService", "Receipt is null, cannot add");
            return;
        }

        db.collection("receipts")
                .add(receipt)
                .addOnSuccessListener(documentReference -> {
                    Log.d("FirestoreService", "Receipt added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error adding receipt", e);
                });
    }

    // Read: Get a receipt by ID
    public void getReceipt(String receiptId, FirestoreCallback<Receipt> callback) {
        db.collection("receipts")
                .document(receiptId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Receipt receipt = documentSnapshot.toObject(Receipt.class);
                        if (callback != null) callback.onSuccess(receipt);
                    } else {
                        Log.e("FirestoreService", "No receipt found with ID: " + receiptId);
                        if (callback != null) callback.onFailure(new Exception("Receipt not found"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error fetching receipt", e);
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Read: Get all receipts
    public void getAllReceipts(FirestoreCallback<List<Receipt>> callback) {
        db.collection("receipts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Receipt> receipts = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        receipts.add(document.toObject(Receipt.class));
                    }
                    if (callback != null) callback.onSuccess(receipts);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error fetching receipts", e);
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Update: Update a receipt by ID
    public void updateReceipt(String receiptId, Receipt updatedReceipt) {
        if (updatedReceipt == null) {
            Log.e("FirestoreService", "Updated receipt is null, cannot update");
            return;
        }

        db.collection("receipts")
                .document(receiptId)
                .set(updatedReceipt)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreService", "Receipt updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error updating receipt", e);
                });
    }

    // Delete: Delete a receipt by ID
    public void deleteReceipt(String receiptId) {
        db.collection("receipts")
                .document(receiptId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreService", "Receipt deleted successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error deleting receipt", e);
                });
    }

    // -------------------- CRUD for Items --------------------

    // Create: Add a new item
    public void addItem(Item item) {
        if (item == null) {
            Log.e("FirestoreService", "Item is null, cannot add");
            return;
        }

        db.collection("items")
                .add(item)
                .addOnSuccessListener(documentReference -> {
                    Log.d("FirestoreService", "Item added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error adding item", e);
                });
    }

    // Read: Get an item by ID
    public void getItem(String itemId, FirestoreCallback<Item> callback) {
        db.collection("items")
                .document(itemId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Item item = documentSnapshot.toObject(Item.class);
                        if (callback != null) callback.onSuccess(item);
                    } else {
                        Log.e("FirestoreService", "No item found with ID: " + itemId);
                        if (callback != null) callback.onFailure(new Exception("Item not found"));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error fetching item", e);
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Read: Get all items
    public void getAllItems(FirestoreCallback<List<Item>> callback) {
        db.collection("items")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Item> items = new ArrayList<>();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        items.add(document.toObject(Item.class));
                    }
                    if (callback != null) callback.onSuccess(items);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error fetching items", e);
                    if (callback != null) callback.onFailure(e);
                });
    }

    // Update: Update an item by ID
    public void updateItem(String itemId, Item updatedItem) {
        if (updatedItem == null) {
            Log.e("FirestoreService", "Updated item is null, cannot update");
            return;
        }

        db.collection("items")
                .document(itemId)
                .set(updatedItem)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreService", "Item updated successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error updating item", e);
                });
    }

    // Delete: Delete an item by ID
    public void deleteItem(String itemId) {
        db.collection("items")
                .document(itemId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreService", "Item deleted successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreService", "Error deleting item", e);
                });
    }

    // -------------------- Callback Interface --------------------

    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }
}
