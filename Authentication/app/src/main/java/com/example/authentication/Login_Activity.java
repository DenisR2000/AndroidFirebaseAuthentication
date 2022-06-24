package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Login_Activity extends AppCompatActivity {

    private TextInputEditText inpEmail;
    private TextInputEditText inpPass;
    private TextView tvRegisterHere;
    private Button btnLogin;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_login);

        inpEmail = findViewById(R.id.input_login);
        inpPass = findViewById(R.id.input_pass);
        tvRegisterHere = findViewById(R.id.reg_here);
        btnLogin = findViewById(R.id.btn_login);

        myAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        tvRegisterHere.setOnClickListener(view -> {
            startActivity(new Intent(Login_Activity.this, Register_Activity.class));
        });
    }

    private void  loginUser() {
        String email = Objects.requireNonNull(inpEmail.getText()).toString();
        String pass = Objects.requireNonNull(inpPass.getText()).toString();

        if(TextUtils.isEmpty(email)) {
            inpEmail.requestFocus();
        }
        else if(TextUtils.isEmpty(pass)) {
            inpPass.requestFocus();
        }
        else {
            myAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(Login_Activity.this, "Successful login", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login_Activity.this, MainActivity.class));
                    } else {
                        Toast.makeText(Login_Activity.this, "Error: " +
                                task.getException().getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}