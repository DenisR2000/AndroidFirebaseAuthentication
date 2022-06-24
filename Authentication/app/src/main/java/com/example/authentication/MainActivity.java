package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnLogout;
    private FirebaseAuth myAuth;
    private ImageView imageMenu;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        imageMenu = findViewById(R.id.imageMenu);
        btnLogout = findViewById(R.id.btn_logout);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        final LoadingDialog loadingDialog = new LoadingDialog(MainActivity.this);

        myAuth = FirebaseAuth.getInstance();

        btnLogout.setOnClickListener(view -> {
            loadingDialog.startLoadingDialog();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    myAuth.signOut();
                    loadingDialog.dismissDialog();
                }
            }, 2000);

            startActivity(new Intent(MainActivity.this, Login_Activity.class));
        });

        imageMenu.setOnClickListener(view -> {
            drawerLayout.openDrawer(GravityCompat.START);
        });

        navigationView.setItemIconTintList(null);
        NavController navController = Navigation.findNavController(this, R.id.navigationHostFragment);
        NavigationUI .setupWithNavController(navigationView, navController);
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseUser user = myAuth.getCurrentUser();
        if(user == null){
            startActivity(new Intent(MainActivity.this, Hello_Activity.class));
        }
    }
}