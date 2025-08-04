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

 import com.google.firebase.auth.FirebaseAuth;

 public class LoginPage extends AppCompatActivity {

    EditText email,password;
    FirebaseAuth auth =FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login_page);

        AppCompatButton loginbtn=findViewById(R.id.loginbtn);
        email=findViewById(R.id.useremail);
        password=findViewById(R.id.userpassword);
        TextView createAccountTxt=findViewById(R.id.createAccount);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String Eemail=email.getText().toString().trim();
               String Epassword=password.getText().toString().trim();

               auth.signInWithEmailAndPassword(Eemail,Epassword).addOnSuccessListener(authResult -> {
                    String userid=authResult.getUser().getUid();
                   Log.d("firebaselogin", userid);
                   Intent intent=new Intent(LoginPage.this,dashboard.class);
                   intent.putExtra("userid",userid);
                   startActivity(intent);
                   Toast.makeText(LoginPage.this,"Login Sucessfully",Toast.LENGTH_LONG).show();
               }).addOnFailureListener(e -> {
                   Toast.makeText(LoginPage.this, "faileded login", Toast.LENGTH_SHORT).show();
               });

            }
        });

        createAccountTxt.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);
        });

    }
}