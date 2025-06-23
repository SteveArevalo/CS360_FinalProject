package com.zybooks.stevearevalo_warehouseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Main dashboard activity to view, add, and delete inventory items
public class InventoryDashboardActivity extends AppCompatActivity {

    private InventoryDatabaseHelper dbHelper;        // Handles database operations
    private InventoryAdapter adapter;                // Adapter for RecyclerView
    private ArrayList<InventoryItem> itemList;       // List of inventory items

    private EditText addItemName, addItemQuantity;   // Input fields for new item
    private Button buttonAddItem, buttonOpenSmsNotifications; // Buttons for adding item and opening SMS screen
    private RecyclerView recyclerViewInventory;      // RecyclerView to display inventory

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_dashboard);

        // Initialize database helper
        dbHelper = new InventoryDatabaseHelper(this);

        // Connect UI components to their XML layout IDs
        addItemName = findViewById(R.id.addItemName);
        addItemQuantity = findViewById(R.id.addItemQuantity);
        buttonAddItem = findViewById(R.id.buttonAddItem);
        recyclerViewInventory = findViewById(R.id.recyclerViewInventory);
        buttonOpenSmsNotifications = findViewById(R.id.buttonOpenSmsNotifications);

        // Set layout manager for the RecyclerView
        recyclerViewInventory.setLayoutManager(new LinearLayoutManager(this));

        // Load items from database
        itemList = dbHelper.getAllItems();

        // Create adapter and handle item deletion through lambda function
        adapter = new InventoryAdapter(itemList, id -> {
            dbHelper.deleteItem(id);  // Delete from DB
            refreshList();           // Refresh UI list
        });

        recyclerViewInventory.setAdapter(adapter);

        // Add button click logic
        buttonAddItem.setOnClickListener(v -> {
            String name = addItemName.getText().toString().trim();
            String qtyStr = addItemQuantity.getText().toString().trim();

            // Validate input fields
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(qtyStr)) {
                Toast.makeText(this, "Please enter both name and quantity", Toast.LENGTH_SHORT).show();
                return;
            }

            int qty;
            try {
                qty = Integer.parseInt(qtyStr); // Ensure quantity is a valid number
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show();
                return;
            }

            // Add item to database and clear input fields
            dbHelper.addItem(name, qty);
            addItemName.setText("");
            addItemQuantity.setText("");
            refreshList();
        });

        // Open the SMS Notification Activity when the button is clicked
        buttonOpenSmsNotifications.setOnClickListener(v -> {
            Intent intent = new Intent(InventoryDashboardActivity.this, SmsNotificationActivity.class);
            startActivity(intent);
        });
    }

    // Reload inventory items from the database and refresh adapter
    private void refreshList() {
        itemList.clear();
        itemList.addAll(dbHelper.getAllItems());
        adapter.notifyDataSetChanged();
    }
}
