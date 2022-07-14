package com.inamul.expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Objects.requireNonNull(getSupportActionBar()).hide();
        mAuth = FirebaseAuth.getInstance();

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

    }

    private void logOut() {
        mAuth.signOut();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}