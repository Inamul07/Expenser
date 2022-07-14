package com.inamul.expenser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmailText, loginPasswordText;
    Button btnLogin;
    TextView txtViewSignup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmailText = findViewById(R.id.loginEmailText);
        loginPasswordText = findViewById(R.id.loginPasswordText);
        btnLogin = findViewById(R.id.btnLogin);
        txtViewSignup = findViewById(R.id.txtViewSignup);
        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        btnLogin.setOnClickListener((v) -> {
            String email = loginEmailText.getText().toString(), password = loginPasswordText.getText().toString();
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                loginEmailText.setError("Enter a valid e-mail");
                loginEmailText.requestFocus();
            } else if(password.isEmpty()) {
                loginPasswordText.setError("Password cannot be empty");
                loginPasswordText.requestFocus();
            } else {
                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setTitle("Logging In");
                progressDialog.setMessage("This may take a few seconds");
                progressDialog.show();
                loginEmailText.setEnabled(false);
                loginPasswordText.setEnabled(false);
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error Occured " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            loginEmailText.setEnabled(true);
                            loginPasswordText.setEnabled(true);
                        }
                    }
                });
            }
        });

        txtViewSignup.setOnClickListener((v) -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });

    }
}