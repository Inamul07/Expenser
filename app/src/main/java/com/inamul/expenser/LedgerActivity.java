package com.inamul.expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inamul.ledgers.Expense;
import com.inamul.ledgers.ExpenseAdapter;
import com.inamul.ledgers.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.Objects;

public class LedgerActivity extends AppCompatActivity implements RecyclerViewInterface {

    TextView remAmount;
    RecyclerView expenseList;
    Button btnCredit, btnDebit;
    ExpenseAdapter expenseAdapter;
    ArrayList<Expense> list;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String ledgerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ledger);

        ledgerName = getIntent().getStringExtra("NAME");
        String totalAmount = getIntent().getStringExtra("AMOUNT");
        Objects.requireNonNull(getSupportActionBar()).setTitle(ledgerName);

        mAuth = FirebaseAuth.getInstance();
        remAmount = findViewById(R.id.remAmountText);
        btnCredit = findViewById(R.id.btnCredit);
        btnDebit = findViewById(R.id.btnDebit);
        expenseList = findViewById(R.id.expenseList);
        expenseList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LedgerActivity.this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
        expenseList.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(LedgerActivity.this, list, this);
        expenseList.setAdapter(expenseAdapter);
        String path = Objects.requireNonNull(mAuth.getUid()) + "/ledgers/" + ledgerName + "/expenses/";
        databaseReference = FirebaseDatabase.getInstance().getReference(path);

        remAmount.setText(totalAmount);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Expense expense = dataSnapshot.getValue(Expense.class);
                    list.add(expense);
                }
                expenseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerActivity.this, ExpenseActivity.class);
                intent.putExtra("LEDGER_NAME", ledgerName);
                intent.putExtra("LEDGER_AMOUNT", totalAmount);
                intent.putExtra("TYPE", "Credit");
                startActivity(intent);
                finish();
            }
        });

        btnDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LedgerActivity.this, ExpenseActivity.class);
                intent.putExtra("LEDGER_NAME", ledgerName);
                intent.putExtra("LEDGER_AMOUNT", totalAmount);
                intent.putExtra("TYPE", "Debit");
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onItemLongClicked(int position) {
        String key = list.get(position).getKey();
        new MaterialAlertDialogBuilder(LedgerActivity.this).setTitle("Delete Record ?")
                .setMessage("Once deleted, the data cannot be retrieved")
                .setPositiveButton("Ok", (dialog, which) -> {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(mAuth.getUid() + "/" + ledgerName + "/expenses/" + key);
                    ref.removeValue();
                    expenseAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                }).show();
    }
}