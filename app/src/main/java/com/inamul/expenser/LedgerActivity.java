package com.inamul.expenser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerActivity.this, ExpenseActivity.class);
                intent.putExtra("LEDGER_NAME", ledgerName);
                intent.putExtra("TYPE", "Credit");
                startActivity(intent);
            }
        });

        btnDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerActivity.this, ExpenseActivity.class);
                intent.putExtra("TYPE", "Debit");
                startActivity(intent);
            }
        });

    }

    private void updateRemAmount(String totalAmount) {
        totalAmount = "₹ " + totalAmount;
        remAmount.setText(totalAmount);
    }
}