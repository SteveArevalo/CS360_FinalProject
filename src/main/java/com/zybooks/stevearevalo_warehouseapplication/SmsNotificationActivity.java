package com.zybooks.stevearevalo_warehouseapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Activity to handle SMS notification functionality.
 * Users can request SMS permissions and send alerts when inventory is low.
 */
public class SmsNotificationActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 101; // Request code for permission

    // UI elements
    private TextView textViewPermissionStatus;
    private EditText editTextPhoneNumber, editTextMessage;
    private Button buttonRequestPermission, buttonSendSms, btnLowInventory;

    private boolean smsPermissionGranted = false; // Flag to track permission state

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_notifications); // Ensure this matches your layout XML

        // Initialize UI references
        textViewPermissionStatus = findViewById(R.id.textViewPermissionStatus);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonRequestPermission = findViewById(R.id.buttonRequestPermission);
        buttonSendSms = findViewById(R.id.buttonSendSms);
        btnLowInventory = findViewById(R.id.btnLowInventory);

        // Check if SMS permission is already granted
        smsPermissionGranted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;

        updatePermissionStatus(); // Reflect current status in the UI

        // Handle click to request permission
        buttonRequestPermission.setOnClickListener(v -> requestSmsPermission());

        // Send custom message if permission is granted
        buttonSendSms.setOnClickListener(v -> {
            if (smsPermissionGranted) {
                sendCustomSms();
            } else {
                Toast.makeText(this, "Permission not granted to send SMS.", Toast.LENGTH_SHORT).show();
            }
        });

        // Send a predefined alert for low inventory
        btnLowInventory.setOnClickListener(v -> {
            if (smsPermissionGranted) {
                sendLowInventoryAlert();
            } else {
                Toast.makeText(this, "Permission not granted to send SMS.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Requests SMS send permission from the user.
     */
    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},
                SMS_PERMISSION_CODE);
    }

    /**
     * Handles result of permission request.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // If permission granted, update status
        if (requestCode == SMS_PERMISSION_CODE) {
            smsPermissionGranted = grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED;

            updatePermissionStatus(); // Update the UI status label

            if (smsPermissionGranted) {
                Toast.makeText(this, "SMS permission granted.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Sends a custom SMS entered by the user.
     */
    private void sendCustomSms() {
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();

        if (phoneNumber.isEmpty() || message.isEmpty()) {
            Toast.makeText(this, "Please enter both phone number and message.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Message sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Sends a predefined low inventory alert SMS.
     */
    private void sendLowInventoryAlert() {
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        String message = "⚠️ Alert: Inventory is low on critical item(s). Please restock ASAP.";

        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a phone number.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "Low inventory alert sent!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send alert: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Updates the permission status display on screen.
     */
    private void updatePermissionStatus() {
        textViewPermissionStatus.setText(
                "Permission status: " + (smsPermissionGranted ? "Granted" : "Denied"));
    }
}
