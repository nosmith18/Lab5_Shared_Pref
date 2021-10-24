package com.example.lab5_shared_pref;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public void displayUsername(View view) {
        EditText usernameText = findViewById(R.id.editText_Username);
        String username = usernameText.getText().toString();
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.persistentstorage", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("username", username).apply();
        switchIntent(username);
    }

    public void switchIntent(String username){
        Intent intent = new Intent(this, MainActivity2.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String usernameKey = "username";
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.persistentstorage", Context.MODE_PRIVATE);
        if(!sharedPreferences.getString(usernameKey, "").equals("")) {
            String name = sharedPreferences.getString(usernameKey, "");
            switchIntent(name);
        } else {
            setContentView(R.layout.activity_main);
        }
    }
}