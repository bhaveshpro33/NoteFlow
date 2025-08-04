package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class mainlogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mainlogo);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                if (auth.getCurrentUser() != null) {
                    // User is already logged in
                    String userid =auth.getCurrentUser().getUid();
                    Intent intent = new Intent(mainlogo.this, dashboard.class); // or your notes activity
                    intent.putExtra("userid",userid);
                    finish();
                    startActivity(intent);
                } else {
                    // User not logged in
                    Intent intent = new Intent(mainlogo.this, LoginPage.class);
                    startActivity(intent);
                }

            }
        },3000);
    }
}