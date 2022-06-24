package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Register_Activity extends AppCompatActivity {

    private TextInputEditText inpRegEmail;
    private TextInputEditText inpRegPass;
    private TextInputEditText inpConfirmPass;
    private TextView tvLLoginHEre;
    private Button btnRegister;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_register);

        inpRegEmail = findViewById(R.id.input_new_login);
        inpRegPass = findViewById(R.id.input_new_pass);
        tvLLoginHEre = findViewById(R.id.login_here);
        btnRegister = findViewById(R.id.btn_register);
        inpConfirmPass = findViewById(R.id.input_new_confirm_pass);

        myAuth = FirebaseAuth.getInstance(); //статичный метод который выполняет подключение

        btnRegister.setOnClickListener(view -> {
            createUser();
        });

        tvLLoginHEre.setOnClickListener(view -> {
            startActivity(new Intent(Register_Activity.this, Login_Activity.class));
        });
    }

    private void createUser() {
        String email = Objects.requireNonNull(inpRegEmail.getText()).toString();
        String pass = Objects.requireNonNull(inpRegPass.getText()).toString();
        String confirmPass = Objects.requireNonNull(inpConfirmPass.getText()).toString();
        if (TextUtils.isEmpty(email)) {
            inpRegEmail.requestFocus();
        }
        else if (TextUtils.isEmpty(pass)) {
            inpRegPass.requestFocus();
        }
        else if(TextUtils.isEmpty(confirmPass)){
            inpConfirmPass.requestFocus();
        }
        else {
            if(pass.equals(confirmPass)) {
                myAuth.createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(Register_Activity.this, "Successful", Toast.LENGTH_SHORT).show();
                                    myAuth.signInWithEmailAndPassword(email, pass);
                                    FirebaseUser user = myAuth.getCurrentUser();
                                    if(user != null) {
                                        startActivity(new Intent(Register_Activity.this, MainActivity.class));
                                    } else {
                                        Toast.makeText(Register_Activity.this, "NUll user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(Register_Activity.this, "Error: " +
                                                    Objects.requireNonNull(task.getException()).getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }
}