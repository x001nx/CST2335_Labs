package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {
    Button button;
    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        button = (Button) findViewById(R.id.MyButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                startActivityForResult(intent, 50);

            }
        });}

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode == 50) {
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
        if (responseCode == Activity.RESULT_OK) {
            String messagePassed = data.getStringExtra("Response");
            int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off

            Toast toast = Toast.makeText(this , messagePassed, duration); //this is the ListActivity
            toast.show(); //display your message box

        }
    }

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
