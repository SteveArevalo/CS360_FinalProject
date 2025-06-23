package com.zybooks.stevearevalo_warehouseapplication;

/**
 * A simple model class representing an inventory item.
 * Each item has a unique ID, a name, and a quantity.
 */
public class InventoryItem {
    private int id;          // Unique identifier for the item (primary key from the database)
    private String name;     // Name/label of the item
    private int quantity;    // Quantity of the item in stock

    // Constructor to initialize all fields
    public InventoryItem(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    // Getter for item ID
    public int getId() {
        return id;
    }

    // Getter for item name
    public String getName() {
        return name;
    }

    // Getter for item quantity
    public int getQuantity() {
        return quantity;
    }
}
