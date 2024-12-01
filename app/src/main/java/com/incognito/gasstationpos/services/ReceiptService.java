package com.incognito.gasstationpos.services;

import com.incognito.gasstationpos.models.Item;
import com.incognito.gasstationpos.models.Receipt;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReceiptService {

    private final String csvFilePath;
    private final List<Receipt> receipts;

    public ReceiptService(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.receipts = loadReceiptsFromCSV();
    }

    public List<Receipt> loadReceiptsFromCSV() {
        List<Receipt> receiptList = new ArrayList<>();
        File file = new File(csvFilePath);

        // Check if file exists, if not, create an empty file
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                System.err.println("Error creating CSV file: " + e.getMessage());
                return receiptList;
            }
        }

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                double value = Double.parseDouble(line[1]);
                long timestamp = Long.parseLong(line[2]); // Assuming the date is stored as timestamp
                String date = line[3]; // The date as String from CSV
                String details = line[4]; // Additional details from CSV
                receiptList.add(new Receipt(value, timestamp));
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        return receiptList;
    }


    public void saveReceiptsToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            writer.writeNext(new String[]{"id", "value", "date", "details"});
            for (Receipt receipt : receipts) {
                writer.writeNext(new String[]{
                        receipt.getId(),
                        String.valueOf(receipt.getValue()),
                        String.valueOf(receipt.getTimestamp())
                });
            }
        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Create a new receipt
    public void createReceipt(Receipt receipt) {
        receipt.setId(UUID.randomUUID().toString());  // Assign a new unique ID
        receipts.add(receipt);
        saveReceiptsToCSV();
    }

    // Get all receipts
    public List<Receipt> getAllReceipts() {
        return new ArrayList<>(receipts);
    }

    // Get a receipt by ID
    public Receipt getReceiptById(String id) {
        return receipts.stream()
                .filter(receipt -> receipt.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update an existing receipt
    public boolean updateReceipt(String id, Receipt updatedReceipt) {
        for (int i = 0; i < receipts.size(); i++) {
            if (receipts.get(i).getId().equals(id)) {
                updatedReceipt.setId(id);  // Retain original ID
                receipts.set(i, updatedReceipt);
                saveReceiptsToCSV();
                return true;
            }
        }
        return false;
    }

    // Delete a receipt by ID
    public boolean deleteReceipt(String id) {
        boolean removed = receipts.removeIf(receipt -> receipt.getId().equals(id));
        if (removed) {
            saveReceiptsToCSV();
        }
        return removed;
    }

    // Helper: Serialize items into a single string
    private String serializeItems(List<Item> items) {
        StringBuilder serialized = new StringBuilder();
        for (Item item : items) {
            serialized.append(item.getId()).append(":")
                    .append(item.getName()).append(":")
                    .append(item.getValue()).append(":")
                    .append(item.getQuantity()).append(":")
                    .append(item.getCategory()).append(";");
        }
        return serialized.toString();
    }

    // Helper: Deserialize items from a string
    private List<Item> deserializeItems(String data) {
        List<Item> items = new ArrayList<>();
        if (data != null && !data.isEmpty()) {
            String[] parts = data.split(";");
            for (String part : parts) {
                String[] itemData = part.split(":");
                if (itemData.length == 5) {
                    Item item = new Item(
                            itemData[1],   // name
                            "",            // image (not serialized)
                            Double.parseDouble(itemData[2]), // value
                            Integer.parseInt(itemData[3]),   // quantity
                            itemData[4]    // category
                    );
                    item.setId(itemData[0]);
                    items.add(item);
                }
            }
        }
        return items;
    }
}
