package com.zybooks.stevearevalo_warehouseapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter class to bind inventory data to RecyclerView rows
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder> {

    private List<InventoryItem> inventoryList; // List holding inventory data
    private OnDeleteClickListener deleteClickListener; // Listener for delete button clicks

    // Constructor to initialize the adapter with the inventory list and delete listener
    public InventoryAdapter(List<InventoryItem> inventoryList, OnDeleteClickListener deleteClickListener) {
        this.inventoryList = inventoryList;
        this.deleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate a layout for each item in the inventory list
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_inventory_row, parent, false);
        return new InventoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        // Bind inventory data to the views in the ViewHolder
        InventoryItem item = inventoryList.get(position);
        holder.itemNameTextView.setText(item.getName()); // Set item name
        holder.itemQuantityTextView.setText(String.valueOf(item.getQuantity())); // Set item quantity

        // Set up delete button to trigger the delete listener when clicked
        holder.deleteButton.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the total number of items in the inventory list
        return inventoryList.size();
    }

    // Update the adapter with new data and refresh the RecyclerView
    public void updateData(List<InventoryItem> inventoryList) {
        this.inventoryList = inventoryList;
        notifyDataSetChanged(); // Notify the RecyclerView to redraw items
    }

    // ViewHolder class holds references to the UI elements for each inventory row
    public static class InventoryViewHolder extends RecyclerView.ViewHolder {
        TextView itemNameTextView;
        TextView itemQuantityTextView;
        Button deleteButton;

        public InventoryViewHolder(@NonNull View itemView) {
            super(itemView);
            // Find views by ID from the row layout
            itemNameTextView = itemView.findViewById(R.id.Item_Name_Text);
            itemQuantityTextView = itemView.findViewById(R.id.Item_Quantity_Text);
            deleteButton = itemView.findViewById(R.id.Delete_Button);
        }
    }

    // Interface to handle delete button click events
    public interface OnDeleteClickListener {
        void onDeleteClick(int itemId); // Method to be implemented in the activity
    }
}
