package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "In onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText txtLoginEmail = (EditText) findViewById(R.id.loginEmail);
        Button buttonBack;
        Button buttonNext;
        Button buttonLogin;
        SharedPreferences sharedPref = getSharedPreferences("cst2335.lab3", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPref.edit();

        txtLoginEmail.setText(sharedPref.getString("login_email", "Defautl@Default.com"));

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("login_email", txtLoginEmail.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });

        buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(myIntent);
            }
        });

        buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(LoginActivity.this, ListItemsActivity.class);
                startActivity(myIntent);
            }

        });}

        public void onResume() {
            super.onResume();
            Log.i(ACTIVITY_NAME, "In onResume()");
        }

        public void onStart() {
            super.onStart();
            Log.i(ACTIVITY_NAME, "In onStart()");
        }
        public void onPause(){
            super.onPause();
            Log.i(ACTIVITY_NAME, "In onPause()");
        }
        public void onStop(){
            super.onStop();
            Log.i(ACTIVITY_NAME, "In onStop()");
        }
        public void onDestroy(){
            super.onDestroy();
            Log.i(ACTIVITY_NAME, "In onDestroy()");
        }

}
