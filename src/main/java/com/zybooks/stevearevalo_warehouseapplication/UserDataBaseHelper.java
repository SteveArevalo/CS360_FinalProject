package com.zybooks.stevearevalo_warehouseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class to manage user login database.
 * Supports registration, validation, and checking if a user exists.
 */
public class UserDataBaseHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DB_NAME = "users.db";
    private static final int DB_VERSION = 1;

    // Table and column names
    private static final String TABLE_NAME = "users";
    private static final String COL_USERNAME = "username";
    private static final String COL_PASSWORD = "password";

    public UserDataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * Called when database is first created. Creates the 'users' table.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_USERNAME + " TEXT PRIMARY KEY, " +       // Username must be unique
                COL_PASSWORD + " TEXT NOT NULL)");           // Password cannot be null
    }

    /**
     * Called when database version changes. Drops and recreates the table.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Registers a new user if the username doesn't already exist.
     * @return true if registration succeeded, false otherwise
     */
    public boolean registerUser(String username, String password) {
        if (userExists(username)) return false; // Prevent duplicate usernames
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    /**
     * Validates user credentials for login.
     * @return true if username and password match a record, false otherwise
     */
    public boolean validateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password}, null, null, null);
        boolean result = cursor.moveToFirst(); // True if a matching row exists
        cursor.close();
        db.close();
        return result;
    }

    /**
     * Checks if a username is already taken.
     * @return true if user exists, false otherwise
     */
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,
                COL_USERNAME + "=?", new String[]{username}, null, null, null);
        boolean exists = cursor.moveToFirst(); // True if username is found
        cursor.close();
        db.close();
        return exists;
    }
}
