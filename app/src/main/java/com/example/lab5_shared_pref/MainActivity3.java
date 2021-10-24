package com.example.lab5_shared_pref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {

    int noteid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText notesInput = (EditText) findViewById(R.id.notesInput);
        Intent intent = getIntent();
        int intentVal = intent.getIntExtra("noteid", -1);
        noteid = intentVal;

        if (noteid != -1) {
            Note note = MainActivity2.notes.get(noteid);
            String noteContent = note.getContent();
            notesInput.setText(noteContent);
        }
    }

    public void saveNote(View view) {
        EditText notesInput = (EditText) findViewById(R.id.notesInput);
        String content = notesInput.getText().toString();

        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.persistentstorage", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        String title;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String date = dateFormat.format(new Date());

        if (noteid == -1) {
            title = "NOTE_" + (MainActivity2.notes.size() + 1);
            dbHelper.saveNotes(username, title, content, date);
        } else {
            title = "NOTE_" + (noteid + 1);
            dbHelper.updateNote(title, date, content, username);
        }

        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}