package com.inamul.expenser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inamul.ledgers.Ledger;
import com.inamul.ledgers.MyAdapter;
import com.inamul.ledgers.RecyclerViewInterface;

import java.util.ArrayList;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity implements RecyclerViewInterface {

    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<Ledger> list;
    LinearLayout emptyListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.ledgerList);
        databaseReference = FirebaseDatabase.getInstance().getReference(mAuth.getUid() + "/ledgers");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
        list = new ArrayList<>();
        myAdapter = new MyAdapter(HomeActivity.this, list, this);
        recyclerView.setAdapter(myAdapter);
        emptyListLayout = findViewById(R.id.empty_list_layout);

        MaterialToolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.logOut) {
                new MaterialAlertDialogBuilder(HomeActivity.this).setTitle("LogOut")
                        .setMessage("Do You Want To LogOut?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", (dialog, which) -> logOut())
                        .setNegativeButton("No", (dialog, which) -> { }).show();
            } else if(item.getItemId() == R.id.createLedger) {
                Intent intent = new Intent(HomeActivity.this, CreateLedgerActivity.class);
                startActivity(intent);
            }
            return false;
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Ledger ledger = dataSnapshot.getValue(Ledger.class);
                    list.add(ledger);
                }
                myAdapter.notifyDataSetChanged();

                if(list.isEmpty()) {
                    emptyListLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void logOut() {
        mAuth.signOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClicked(int position) {
        Intent intent = new Intent(HomeActivity.this, LedgerActivity.class);

        intent.putExtra("NAME", list.get(position).getLedgerName());
        intent.putExtra("AMOUNT", list.get(position).getTotalAmount());

        startActivity(intent);
    }
}