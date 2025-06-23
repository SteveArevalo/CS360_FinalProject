package com.zybooks.stevearevalo_warehouseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

// Helper class to manage SQLite database for inventory items
public class InventoryDatabaseHelper extends SQLiteOpenHelper {

    // Database and table configuration
    private static final String DB_NAME = "warehouse_inventory.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "inventory";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_QTY = "quantity";

    // Constructor to create database helper instance
    public InventoryDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Called once when the database is created
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_QTY + " INTEGER)";
        db.execSQL(query);
    }

    // Called when database version is updated
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Add a new item to the inventory
    public boolean addItem(String name, int qty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_QTY, qty);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    // Delete an item from the inventory by ID
    public boolean deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Get all items from the inventory as a list
    public ArrayList<InventoryItem> getAllItems() {
        ArrayList<InventoryItem> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                int qty = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QTY));
                list.add(new InventoryItem(id, name, qty));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}
