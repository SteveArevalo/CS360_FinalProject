package com.zybooks.stevearevalo_warehouseapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonCreateAccount;
    private UserDataBaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Link UI elements with layout views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);

        // Initialize database helper
        dbHelper = new UserDataBaseHelper(this);

        // Login Button
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (dbHelper.validateUser(username, password)) {
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                // Launch InventoryDashboardActivity
                Intent intent = new Intent(LoginActivity.this, InventoryDashboardActivity.class);
                startActivity(intent);
                finish(); // Optional: prevent going back to login screen
            } else {
                Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
            }
        });

        // Create Account Button
        buttonCreateAccount.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (dbHelper.userExists(username)) {
                Toast.makeText(this, "Username already exists.", Toast.LENGTH_SHORT).show();
            } else {
                boolean success = dbHelper.registerUser(username, password);
                if (success) {
                    Toast.makeText(this, "Account created! You can now log in.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Account creation failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
