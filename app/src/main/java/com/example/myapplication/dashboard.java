package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class dashboard extends AppCompatActivity {

    ArrayList<Notes_Model> notes=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);
        Intent intent =getIntent();
        RecyclerView recyclerView =findViewById(R.id.notes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton createnote=findViewById(R.id.createnote);
        EditText search =findViewById(R.id.search_bar);
        String userid=intent.getStringExtra("userid");
        ImageView setting =findViewById(R.id.setting);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("/users/" + userid + "/notes");

        notesadapter notesadapter=new notesadapter( this,notes,userid);
        recyclerView.setAdapter(notesadapter);
        ValueEventListener noteslistner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notes.clear(); // Clear old data
                for (DataSnapshot child : snapshot.getChildren()) {

                    Notes_Model note = child.getValue(Notes_Model.class);
                    if (note != null) {
                        note.setKey(child.getKey());
                        notes.add(note);
                        Log.d("firebasedata", note.getKey()+" "+ note.getTitle() + " " + note.getDate() + " " + note.getNote());
                    }
                }
                notesadapter.notifyDataSetChanged();
                // Optional: notify adapter here if using RecyclerView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("firebaseerror", "error: " + error.getMessage());
            }
        };

        databaseReference.addValueEventListener(noteslistner);

    createnote.setOnClickListener(view -> {
        Intent intent1=new Intent(this,note.class);
        intent1.putExtra("edit",false);
        intent1.putExtra("userid",userid);
        startActivity(intent1);
    });


    search.addTextChangedListener(new TextWatcher() {
        @Override
        public void afterTextChanged(Editable editable) {

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            notesadapter.filter(charSequence.toString());
        }
    });


    setting.setOnClickListener(view -> {
        PopupMenu settingmenu=new PopupMenu(this,view);
        settingmenu.getMenuInflater().inflate(R.menu.setting,settingmenu.getMenu());
        settingmenu.setOnMenuItemClickListener(menuItem -> {
            if(menuItem.getItemId()==R.id.logout){
                Toast.makeText(this, "clicked ", Toast.LENGTH_SHORT).show();
                FirebaseAuth auth =FirebaseAuth.getInstance();
                auth.signOut();
                Intent intent1 =new Intent(dashboard.this,LoginPage.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent1);
            }
            return true;
        });
        settingmenu.show();
    });

    }





}


