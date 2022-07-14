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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    EditText signupEmailText, signupPasswordText, signupConfirmPasswordText;
    Button btnSignup;
    TextView txtViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        signupEmailText = findViewById(R.id.signupEmailText);
        signupPasswordText = findViewById(R.id.signupPasswordText);
        signupConfirmPasswordText = findViewById(R.id.signupConfirmPasswordText);
        txtViewLogin = findViewById(R.id.txtViewLogin);
        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener((v) -> {
            String email = signupEmailText.getText().toString(), password = signupPasswordText.getText().toString(), confirmPassword = signupConfirmPasswordText.getText().toString();
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                signupEmailText.setError("Enter a valid e-mail");
                signupEmailText.requestFocus();
            } else if(password.isEmpty() || password.length() < 6) {
                signupPasswordText.setError("Password length must be atleast 6");
                signupPasswordText.requestFocus();
            } else if(!confirmPassword.equals(password)){
                signupConfirmPasswordText.setError("The passwords don't match!");
                signupConfirmPasswordText.requestFocus();
            } else {
                ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
                progressDialog.setTitle("Signing Up...");
                progressDialog.setMessage("This may take a few seconds");
                progressDialog.show();
                signupEmailText.setEnabled(false);
                signupPasswordText.setEnabled(false);
                signupConfirmPasswordText.setEnabled(false);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "User Registered Successfully", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(SignupActivity.this, HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, "Cannot create user " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            signupEmailText.setEnabled(true);
                            signupPasswordText.setEnabled(true);
                            signupConfirmPasswordText.setEnabled(true);
                            progressDialog.dismiss();
                        }
                    }
                });
            }
        });

        txtViewLogin.setOnClickListener((v) -> {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        });

    }
}