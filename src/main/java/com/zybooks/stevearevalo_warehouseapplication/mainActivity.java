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
 * This activity handles SMS notifications.
 * It checks for SMS permission, allows the user to request it,
 * and sends either a low inventory alert or a custom message.
 */
public class mainActivity extends AppCompatActivity {

    // Unique request code for identifying the SMS permission request
    private static final int SMS_PERMISSION_REQUEST_CODE = 100;

    // UI components
    private TextView textViewPermissionStatus;
    private Button buttonRequestPermission;
    private Button btnLowInventory;
    private Button buttonSendSms;
    private EditText editTextPhoneNumber;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_notifications); // Inflate the layout for SMS notifications

        // Link XML views to Java variables
        textViewPermissionStatus = findViewById(R.id.textViewPermissionStatus);
        buttonRequestPermission = findViewById(R.id.buttonRequestPermission);
        btnLowInventory = findViewById(R.id.btnLowInventory);
        buttonSendSms = findViewById(R.id.buttonSendSms);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextMessage = findViewById(R.id.editTextMessage);

        // Show the initial SMS permission status
        updatePermissionStatus();

        // Request SMS permission when button is clicked
        buttonRequestPermission.setOnClickListener(v -> requestSmsPermission());

        // Send a predefined low inventory alert via SMS if permission is granted
        btnLowInventory.setOnClickListener(v -> {
            if (hasSmsPermission()) {
                String phone = editTextPhoneNumber.getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(this, "Please enter a phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendSms(phone, "Alert: Inventory is low! Please restock.");
            } else {
                Toast.makeText(this, "SMS permission is not granted", Toast.LENGTH_SHORT).show();
            }
        });

        // Send a custom SMS message if permission is granted
        buttonSendSms.setOnClickListener(v -> {
            if (hasSmsPermission()) {
                String phone = editTextPhoneNumber.getText().toString().trim();
                String message = editTextMessage.getText().toString().trim();
                if (phone.isEmpty() || message.isEmpty()) {
                    Toast.makeText(this, "Please enter phone number and message", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendSms(phone, message);
            } else {
                Toast.makeText(this, "SMS permission is not granted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check if SEND_SMS permission has been granted
    private boolean hasSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Request SMS permission from the user
    private void requestSmsPermission() {
        if (!hasSmsPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    SMS_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(this, "SMS permission already granted", Toast.LENGTH_SHORT).show();
        }
        updatePermissionStatus();
    }

    // Update the UI with the current permission status
    private void updatePermissionStatus() {
        if (hasSmsPermission()) {
            textViewPermissionStatus.setText("Permission status: Granted");
            textViewPermissionStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            textViewPermissionStatus.setText("Permission status: Denied");
            textViewPermissionStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    // Send an SMS message using SmsManager
    private void sendSms(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(this, "SMS sent successfully!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SMS: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Handle the user's response to the SMS permission request
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied. SMS features disabled.", Toast.LENGTH_LONG).show();
            }
            updatePermissionStatus();
        }
    }
}
