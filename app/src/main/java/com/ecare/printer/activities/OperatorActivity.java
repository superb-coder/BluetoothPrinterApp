package com.ecare.printer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ecare.printer.R;
import com.ecare.printer.adapters.CallListAdapter;
import com.ecare.printer.models.Invoice;

import org.w3c.dom.Text;

public class OperatorActivity extends AppCompatActivity {

    Toolbar toolbar;
    Invoice clientInvoice;

    TextView nameView;
    TextView address1View;
    TextView address2View;
    TextView id1View;
    TextView id2View;
    TextView dateView;
    TextView valueView;
    TextView invoiceIdView;
    TextView payStatusView;
    TextView offsetPaidView;
    TextView willbePaidView;

    LinearLayout offset_layout;
    LinearLayout payable_layout;

    String shouldValue;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operator);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        // Set Title in Toolbar
        getSupportActionBar().setTitle("Invoice");

        // Set Back Arrow in Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        clientInvoice = (Invoice) getIntent().getSerializableExtra("invoice_data");

        Log.d("Client Invoice", String.valueOf(clientInvoice));

        nameView = findViewById(R.id.nameView);
        address1View = findViewById(R.id.address1View);
        address2View = findViewById(R.id.address2View);
        id1View = findViewById(R.id.id1View);
        id2View = findViewById(R.id.id2View);
        dateView = findViewById(R.id.dateView);
        valueView = findViewById(R.id.valueView);

        offsetPaidView = findViewById(R.id.offsetValue);
        willbePaidView = findViewById(R.id.willPaid);

        invoiceIdView = findViewById(R.id.invoiceIDView);
        payStatusView = findViewById(R.id.payStatusView);


        nameView.setText(clientInvoice.getName());
        address1View.setText(clientInvoice.getAddress1());
        address2View.setText(clientInvoice.getAddress2());
        id1View.setText(clientInvoice.getId1());
        id2View.setText(clientInvoice.getId2());
        dateView.setText(clientInvoice.getDate());
        valueView.setText("$"+clientInvoice.getInvoiceValue());
        invoiceIdView.setText(clientInvoice.getInvoiceNumber());
        payStatusView.setText("Unpaid");

        payable_layout = findViewById(R.id.payable_layout);
        offset_layout = findViewById(R.id.offset_layout);
        payable_layout.setVisibility(View.GONE);
        offset_layout.setVisibility(View.GONE);

        shouldValue = clientInvoice.getInvoiceValue();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.operator_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
//                Go to Home Activity
                startActivity(new Intent(OperatorActivity.this, HomeActivity.class));
                finish();
                return true;
            case R.id.call_client:
//                Call Client
                showCallList();
                return true;
            case R.id.type_amount:
//                Type Amount
                showTypeAmount();
                return true;
            case R.id.print_receipt:
//                Print Receipt
                printReceipt();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showCallList(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.layout_call_dialog, null);
        ListView callListView = (ListView) row.findViewById(R.id.callList);

        callListView.setAdapter(new CallListAdapter(this, clientInvoice.getNumbers()));

        builder.setView(row);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showTypeAmount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View editAmountView = layoutInflater.inflate(R.layout.layout_type_amount, null);

        builder.setView(editAmountView);

        AlertDialog dialog = builder.create();
        dialog.show();

        EditText editAmount = editAmountView.findViewById(R.id.editAmount);
        Button payButton = editAmountView.findViewById(R.id.payBtn);

        payButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                payable_layout.setVisibility(View.VISIBLE);
                offset_layout.setVisibility(View.VISIBLE);

                String value = editAmount.getText().toString();
                willbePaidView.setText("$" + value);


                String offsetValue = Double.toString(Math.round((Double.parseDouble(shouldValue) - Double.parseDouble(value))*100)/100.0d);
                Log.d("Offset", offsetValue);
                offsetPaidView.setText("$"+offsetValue);
                dialog.hide();
            }
        });

    }

    public void printReceipt(){
        startActivity(new Intent(OperatorActivity.this, PrintReceiptActivity.class));
    }





}