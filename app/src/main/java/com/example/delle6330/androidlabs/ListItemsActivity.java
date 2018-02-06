package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageButton camera;
    Switch switchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        camera = (ImageButton) findViewById(R.id.cameraButton);
        switchItem = (Switch) findViewById(R.id.swithdId);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
        switchItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            final Context context = getApplicationContext();
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    CharSequence text = "Switch is On";// "Switch is Off"
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                    Toast toast = Toast.makeText(context, text, duration);//this is the ListActivity
                    toast.show(); //display your message box
                }
                else {
                    CharSequence text = "Switch is Off";// "Switch is Off"
                    int duration = Toast.LENGTH_SHORT; //= Toast.LENGTH_LONG if Off
                    Toast toast = Toast.makeText(context, text, duration);//this is the ListActivity
                    toast.show(); //display your message box
                }
        }
    });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            camera.setImageBitmap(imageBitmap);
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
