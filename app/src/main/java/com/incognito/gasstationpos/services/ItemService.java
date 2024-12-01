package com.incognito.gasstationpos.services;

import android.util.Log;

import com.incognito.gasstationpos.models.Item;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ItemService {

    private final String csvFilePath;
    private final List<Item> items;

    public ItemService(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.items = loadItemsFromCSV();
    }

    // Load items from CSV at startup
    public List<Item> loadItemsFromCSV() {
        List<Item> itemList = new ArrayList<>();
        File file = new File(csvFilePath);

        // Check if file exists, if not, create an empty file
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                System.err.println("Error creating CSV file: " + e.getMessage());
                return itemList;
            }
        }

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String image = line[2];
                double value = Double.parseDouble(line[3]);
                int quantity = Integer.parseInt(line[4]);
                String category = line[5];
                itemList.add(new Item(name, image, value, quantity, category));
                itemList.get(itemList.size() - 1).setId(id);
            }
        } catch (Exception e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
        File file1 = new File(csvFilePath);
        String folderPath = file1.getParent();  // Get the parent directory path
        Log.d("ItemLog", "CSV Folder Path: " + folderPath);  // Log the folder path
        return itemList;
    }

    private void saveItemsToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath))) {
            writer.writeNext(new String[]{"id", "name", "image", "value", "quantity", "category"});
            for (Item item : items) {
                writer.writeNext(new String[]{
                        item.getId(),
                        item.getName(),
                        item.getImage(),
                        String.valueOf(item.getValue()),
                        String.valueOf(item.getQuantity()),
                        item.getCategory()
                });
            }
        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Create a new item
    public void createItem(Item item) {
        item.setId(UUID.randomUUID().toString());
        items.add(item);
        saveItemsToCSV();
    }

    // Get all items
    public List<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    // Get an item by ID
    public Item getItemById(String id) {
        return items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    // Update an existing item
    public boolean updateItem(String id, Item updatedItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getId().equals(id)) {
                updatedItem.setId(id); // Retain original ID
                items.set(i, updatedItem);
                saveItemsToCSV();
                return true;
            }
        }
        return false;
    }

    // Delete an item by ID
    public boolean deleteItem(String id) {
        boolean removed = items.removeIf(item -> item.getId().equals(id));
        if (removed) {
            saveItemsToCSV();
        }
        return removed;
    }

    // Delete all items
    public void deleteAllItems() {
        items.clear();
        saveItemsToCSV();
    }
}
