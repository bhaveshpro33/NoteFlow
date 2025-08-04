package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class note extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note);
        Intent intent = getIntent();

        String userid = intent.getStringExtra("userid");
        boolean edit = intent.getBooleanExtra("edit", false);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM", Locale.getDefault());
        String newdate = sdf.format(new Date());
        EditText editTitle = findViewById(R.id.editTitle);
        EditText editNote = findViewById(R.id.editNote);
        TextView editTime = findViewById(R.id.editTime);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        ImageView delmenu = findViewById(R.id.moreIcon);
        RelativeLayout footer = findViewById(R.id.footer);
            String note_key = intent.getStringExtra("note_key")!=null? intent.getStringExtra("note_key"): "";
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("/users/" + userid + "/notes/" + note_key);
        if (edit) {

            String date = intent.getStringExtra("note_date");
            String title = intent.getStringExtra("note_title");
            String note = intent.getStringExtra("note_description");
            editTitle.setText(title);
            editNote.setText(note);
            editTime.setText(" last edited " + date);

        } else {
            footer.removeAllViews();
        }






        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_save) {
                String newnote = editNote.getText().toString();
                String newtitle = editTitle.getText().toString();


                Map<String, Object> updatenote = new HashMap<>();
                updatenote.put("note", newnote);
                updatenote.put("title", newtitle);
                updatenote.put("date", newdate);
                if (edit) {
                    databaseReference.updateChildren(updatenote).addOnSuccessListener(unused -> {
                        finish();
                    }).addOnFailureListener(e -> {
//                        Toast.makeText(this, "firebase error", Toast.LENGTH_SHORT).show();
                        Log.d("Firebaseerror", "error: " + e.getMessage());
                    });

                }
                else {
                    DatabaseReference databaseReference1 =FirebaseDatabase.getInstance().getReference("/users/"+userid+"/notes");
                    String newnoteid=databaseReference1.push().getKey();
                    databaseReference1.child(newnoteid).setValue(updatenote).addOnSuccessListener(unused -> {
//                        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "firebase error", Toast.LENGTH_SHORT).show();
                        Log.d("Firebaseerror", "error: " + e.getMessage());
                    });
                }

            }
            return true;
        });

        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
        delmenu.setOnClickListener(view -> {
            PopupMenu menu = new PopupMenu(this, view);
            menu.getMenuInflater().inflate(R.menu.items, menu.getMenu());
            menu.setOnMenuItemClickListener(menuItem -> {
                if (menuItem.getItemId() == R.id.delete) {

                    databaseReference.removeValue().addOnSuccessListener(unused -> {
                        finish();
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "firebase error", Toast.LENGTH_SHORT).show();
                        Log.d("Firebaseerror", "error: " + e.getMessage());
                    });

                    return true;
                }
                return false;
            });
            menu.show();
        });
    }
}