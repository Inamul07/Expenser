package com.inamul.expenser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class LedgerActivity extends AppCompatActivity {

    TextView remAmount;
    Button btnCredit, btnDebit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);

        remAmount = findViewById(R.id.remAmountText);
        btnCredit = findViewById(R.id.btnCredit);
        btnDebit = findViewById(R.id.btnDebit);

        String ledgerName = getIntent().getStringExtra("NAME");
        String totalAmount = getIntent().getStringExtra("AMOUNT");
        Objects.requireNonNull(getSupportActionBar()).setTitle(ledgerName);

        updateRemAmount(totalAmount);

    }

    private void updateRemAmount(String totalAmount) {
        totalAmount = "₹ " + totalAmount;
        remAmount.setText(totalAmount);
    }
}