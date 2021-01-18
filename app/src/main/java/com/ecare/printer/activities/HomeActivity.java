package com.ecare.printer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ecare.printer.R;

public class HomeActivity extends AppCompatActivity {

    Button scanBtn;
    Button transferBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        scanBtn = findViewById(R.id.scanBtn);
        transferBtn = findViewById(R.id.transferBtn);

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Scan QR Code...
                startActivity(new Intent(HomeActivity.this, ScanqrActivity.class));
            }
        });

        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Transfer Scanned Data to Server...
            }
        });
    }
}