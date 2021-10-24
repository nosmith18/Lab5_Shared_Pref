package com.example.lab5_shared_pref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    TextView result;
    SharedPreferences sharedPreferences;
    public static ArrayList<Note> notes = new ArrayList<>();
    DBHelper dbHelper;
    Context context;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPreferences = getSharedPreferences("com.example.persistentstorage", Context.MODE_PRIVATE);

        result = (TextView) findViewById(R.id.textView);
        String username = sharedPreferences.getString("username", "");
        String outcome = "Welcome " + username + "!";
        result.setText(outcome);

        context = getApplicationContext();
        sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        dbHelper = new DBHelper(sqLiteDatabase);
        notes = dbHelper.readNotes(username);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }

        // display notes on screen
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity3.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu item) {
        getMenuInflater().inflate(R.menu.menu, item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            sharedPreferences = getSharedPreferences("com.example.persistentstorage", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            startActivity(intent);
            return true;
        }
        if (item.getItemId() == R.id.addNote) {
            Intent intent = new Intent(this, MainActivity3.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}