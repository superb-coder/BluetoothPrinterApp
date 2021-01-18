package com.ecare.printer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.ecare.printer.R;
import com.ecare.printer.models.Invoice;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Scan QR Code and Extract Invoice information
 *  parse information to Other Activities
 *  */

public class ScanqrActivity extends AppCompatActivity {

    SurfaceView surfaceView;
    TextView txtQRCodeView;
    CameraSource cameraSource;

    String intentData = "";

    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanqr);

        surfaceView = findViewById(R.id.surfaceView);
        txtQRCodeView = findViewById(R.id.txtBarCodeValue);
    }


    // Start the Camera Scan. Extract Information from QR Info.
    private void initialiseDetectorsAndSources(){
        Toast.makeText(getApplicationContext(), "QR Code Scanner Started", Toast.LENGTH_SHORT).show();

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS).build();
        cameraSource = new CameraSource.Builder(this, barcodeDetector).setRequestedPreviewSize(1920, 1080).setAutoFocusEnabled(true).build();
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback(){
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanqrActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScanqrActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcode = detections.getDetectedItems();
                if (barcode.size() != 0) {
                    txtQRCodeView.post(new Runnable() {
                        @Override
                        public void run() {

                            intentData = barcode.valueAt(0).displayValue;
                            Toast.makeText(getApplicationContext(), "QR Code Scanned", Toast.LENGTH_LONG).show();
                            try{
                                JSONObject qrObj = new JSONObject(intentData);
                                String name = qrObj.getString("NUME");
                                String address1 = qrObj.getString("ADRESA1");
                                String address2 = qrObj.getString("ADRESA2");
                                String id1 = qrObj.getString("ID1");
                                String id2 = qrObj.getString("ID2");
                                JSONArray numArray = qrObj.getJSONArray("TELEFON");
                                String invoiceNum = qrObj.getString("NUMAR_FACTURA");
                                String date = qrObj.getString("DATA_FACTURA");
                                String value = qrObj.getString("VALOARE_FACTURA");
                                List<String> phoneNums = new ArrayList<>();
                                for(int i = 0; i < numArray.length(); i++){
                                    JSONObject phoneJsonObj = numArray.getJSONObject(i);
                                    String phoneNum = phoneJsonObj.getString("NUMAR");
                                    phoneNums.add(phoneNum);
                                }
                                Invoice user_invoice = new Invoice(name, address1, address2, id1, id2, phoneNums,invoiceNum, date, value);

                                Intent operatorIntent = new Intent(ScanqrActivity.this, OperatorActivity.class);
                                operatorIntent.putExtra("invoice_data", user_invoice);
                                startActivity(operatorIntent);

                            } catch (Throwable t){
                                Log.d("My App", "could not read your QR code Information");
                            }
//                            txtQRCodeView.setText(intentData);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }



}