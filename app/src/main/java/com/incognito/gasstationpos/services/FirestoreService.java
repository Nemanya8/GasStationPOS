package com.incognito.gasstationpos.services;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.incognito.gasstationpos.models.Receipt;

public class FirestoreService {

    private FirebaseFirestore db;

    public FirestoreService() {
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }

    public void addReceipt(Receipt receipt) {
        if (receipt == null) {
            // Handle null receipt
            return;
        }

        // Add receipt to Firestore
        db.collection("receipts")
                .add(receipt)
                .addOnSuccessListener(documentReference -> {
                    // Handle success
                    Log.d("FirestoreService", "Receipt added with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    // Handle failure
                    Log.e("FirestoreService", "Error adding receipt", e);
                });
    }
}
