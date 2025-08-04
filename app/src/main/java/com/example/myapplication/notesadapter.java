package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class notesadapter extends RecyclerView.Adapter<notesadapter.ViewHolder> {
    Context context;
    ArrayList<Notes_Model> notes=new ArrayList<>();
    ArrayList<Notes_Model> allNotes = new ArrayList<>();
    String userid;
    public  notesadapter(Context context, ArrayList<Notes_Model> notes,String userid){
        this.context=context;
        this.notes=notes;
        this.allNotes=notes;
        this.userid=userid;
        Log.d("firebasedata", "notesadapter: "+ notes.size());
    }

    public void updateList(ArrayList<Notes_Model> newList) {
        this.notes = new ArrayList<>(newList);
        notifyDataSetChanged();
    }

    public void filter(String text) {
        ArrayList<Notes_Model> filteredList = new ArrayList<>();
        for (Notes_Model note : allNotes) {
            if (note.title.toLowerCase().contains(text.toLowerCase()) ||
                    note.note.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(note);
            }
        }
        updateList(filteredList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_note,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.note_date.setText(notes.get(position).date);
        holder.note_description.setText(notes.get(position).note);
        holder.note_title.setText(notes.get(position).title);
        holder.itemView.setOnClickListener(view -> {

           Intent intent= new Intent(context,note.class);
           intent.putExtra("userid",userid);
           intent.putExtra("note_key",notes.get(position).key);
           intent.putExtra("note_date",notes.get(position).date);
           intent.putExtra("note_description",notes.get(position).note);
           intent.putExtra("note_title",notes.get(position).title);
           intent.putExtra("edit",true);
         context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView note_date,note_title,note_description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            note_date=itemView.findViewById(R.id.note_date);
            note_title=itemView.findViewById(R.id.note_title);
            note_description=itemView.findViewById(R.id.note_description);
        }
    }
}
