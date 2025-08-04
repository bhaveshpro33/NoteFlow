package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name,email,password;

    FirebaseAuth auth =FirebaseAuth.getInstance();

    DatabaseReference userdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        AppCompatButton btn =findViewById(R.id.btn1);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        TextView logintxt=findViewById(R.id.logintxt);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Ename = name.getText().toString().trim();
                String Eemail = email.getText().toString().trim();
                String Epass = password.getText().toString().trim();

                if (Ename.isEmpty() || Eemail.isEmpty() || Epass.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fill the form", Toast.LENGTH_SHORT).show();
                } else {

                    auth.createUserWithEmailAndPassword(Eemail, Epass).addOnSuccessListener(authResult -> {
                        String userid = authResult.getUser().getUid();
                    addtodb(Ename,userid);
                        Toast.makeText(MainActivity.this, "Create account sucessfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, LoginPage.class);
                        startActivity(intent);
                    }).addOnFailureListener(e -> {
                        Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        Log.e("error",e.toString());
                    });
                }
            }
        });

        logintxt.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, LoginPage.class);
            startActivity(intent);
        });

    }
    public  void  addtodb(String  name,String key){
        userdb=FirebaseDatabase.getInstance().getReference("users");

        userdb.child(key).setValue(name);
    }

}