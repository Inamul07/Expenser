package com.inamul.expenser;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class ExpenseActivity extends AppCompatActivity {

    LinearLayout dateLayout;
    TextView expenseTypeText, dateText;
    EditText expenseNameText, expenseAmountText;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Button btnAddData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        String type = getIntent().getStringExtra("TYPE");
        String ledgerName = getIntent().getStringExtra("LEDGER_NAME");
        String amount = getIntent().getStringExtra("LEDGER_AMOUNT");
        Objects.requireNonNull(getSupportActionBar()).setTitle(ledgerName);
        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference(Objects.requireNonNull(mAuth.getUid()));

        expenseTypeText = findViewById(R.id.expense_type);
        expenseTypeText.setText(type);

        expenseNameText = findViewById(R.id.expenseNameText);
        expenseAmountText = findViewById(R.id.amountText);
        dateLayout = findViewById(R.id.dateLayout);
        dateText = findViewById(R.id.dateText);
        btnAddData = findViewById(R.id.btnAddData);

        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select the date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds()).build();

        dateLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show(getSupportFragmentManager(), "TAG");
                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        dateText.setText(datePicker.getHeaderText());
                        dateText.setTextColor(Color.BLACK);
                    }
                });
            }
        });

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expenseType = expenseTypeText.getText().toString(), expenseName = expenseNameText.getText().toString(), expenseAmount = expenseAmountText.getText().toString();
                String date = dateText.getText().toString();
                if(expenseName.isEmpty()) {
                    expenseNameText.setError("Expense Name cannot be empty");
                    expenseNameText.requestFocus();
                } else if(expenseAmount.isEmpty()) {
                    expenseAmountText.setError("Expense Amount cannot be empty");
                    expenseAmountText.requestFocus();
                } else if(date.equals("Select Date")) {
                    AlertDialog alertDialog = new MaterialAlertDialogBuilder(ExpenseActivity.this)
                            .setTitle("Invalid Date")
                            .setMessage("Please Select a date")
                            .setPositiveButton("Close", (dialog, which) -> { })
                            .show();
                } else {
                    Map<String, String> map = new TreeMap<>();
                    map.put("expenseType", expenseType);
                    map.put("expenseName", expenseName);
                    map.put("expenseAmount", expenseAmount);
                    map.put("date", date);
                    databaseReference.child("ledgers").child(ledgerName).child("expenses").push().setValue(map);
                    Toast.makeText(ExpenseActivity.this, "Data added successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ExpenseActivity.this, LedgerActivity.class);
                    String balAmount = getIntent().getStringExtra("LEDGER_AMOUNT");
                    if(type.equals("Credit")) {
                        int remAmount = Integer.parseInt(balAmount) + Integer.parseInt(expenseAmount);
                        balAmount = Integer.toString(remAmount);
                    } else if(type.equals("Debit")) {
                        int remAmount = Integer.parseInt(balAmount) - Integer.parseInt(expenseAmount);
                        balAmount = Integer.toString(remAmount);
                    }
                    databaseReference.child("ledgers").child(ledgerName).child("totalAmount").setValue(balAmount);
                    intent.putExtra("NAME", ledgerName);
                    intent.putExtra("AMOUNT", balAmount);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}