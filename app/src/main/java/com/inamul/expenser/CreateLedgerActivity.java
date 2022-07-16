package com.inamul.expenser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class CreateLedgerActivity extends AppCompatActivity {

    EditText ledgerNameText, totalAmountText;
    Button btnCreateLedger;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ledger);

        ledgerNameText = findViewById(R.id.ledgerNameText);
        totalAmountText = findViewById(R.id.totalAmountText);
        btnCreateLedger = findViewById(R.id.btnCreateLedger);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        btnCreateLedger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ledgerName = ledgerNameText.getText().toString(), totalAmount = totalAmountText.getText().toString();
                if(ledgerName.isEmpty() || ledgerName.length() < 3) {
                    ledgerNameText.setError("Ledger name length must be atleast 3");
                    ledgerNameText.requestFocus();
                } else if(totalAmount.isEmpty()) {
                    totalAmountText.setError("Amount cannot be empty");
                    totalAmountText.requestFocus();
                } else {
                    Map<String, String> map = new LinkedHashMap<>();
                    map.put("ledgerName", ledgerName);
                    map.put("totalAmount", totalAmount);
                    mDatabase.child(Objects.requireNonNull(mAuth.getUid())).child("ledgers").child(ledgerName).setValue(map);
                    Toast.makeText(CreateLedgerActivity.this, "Ledger Created Successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(CreateLedgerActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}