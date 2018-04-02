package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MessageDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        //Load fragment to phone:
        Bundle infoToPass = getIntent().getExtras();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        MessageFragment mf  =  new MessageFragment();
        mf.setArguments( infoToPass );
        ft.addToBackStack("Any name, not used"); //only undo FT on back button
        ft.replace(  R.id.phone_frame , mf);
        ft.commit();
    }
}
