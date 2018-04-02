package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {
    ListView listView;
    EditText editText;
    Button button;
    //ArrayList of messages
    ArrayList<String> arrayChat;
    Context ctx;
    ChatAdapter messageAdapter;
    ChatDatabaseHelper dbHelper;
    SQLiteDatabase db;
    ContentValues cv;
    Cursor cursor;
    Boolean isTablet = false;
    MessageFragment messageFragment;

    protected static final String ACTIVITY_NAME = "ChatWindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageAdapter = new ChatAdapter(this);
        //Set layout of the activity
        setContentView(R.layout.activity_chat_window);
        ctx = getApplicationContext();
        FrameLayout frame = findViewById(R.id.frame);

        listView = (ListView) findViewById(R.id.listView);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.sendButton);
        arrayChat = new ArrayList<String>();

        final int orientation = getResources().getConfiguration().orientation;
        Log.i("******* ORIENTATION", String.valueOf(orientation));

        if(frame != null) {
             isTablet = true;
            Log.i("****** Frame loaded", "Table " + isTablet);
        }
        else {
            Log.i("****** Frame loaded", "Table " + isTablet);

        }

        // DATABASE =================
        //DatabaseHelper
        dbHelper = new ChatDatabaseHelper(this);
        //Get WritableDatabase
        db = dbHelper.getWritableDatabase();
        //ContentView
        cv = new ContentValues();

        cursor = db.rawQuery("SELECT * FROM CHAT_TABLE;", null);
        String test = String.valueOf(cursor.getCount());
//        Log.d("********Lab5 " + ACTIVITY_NAME, "c.getCount = " + test);
        cursor.moveToFirst();
        try {
            while (!cursor.isAfterLast()) {
//                Log.i("********Lab5 " + ACTIVITY_NAME, "SQL MESSAGE: " +
//                        cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)) + " Cursor possition " +
//                        cursor.getPosition());
                arrayChat.add(cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            Log.i("Crash", e.getMessage());
         }

        // DATABASE =================
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Store bundle info
                Bundle infoToPass = new Bundle();
                infoToPass.putBoolean("isTablet", isTablet);
                infoToPass.putLong("textID", messageAdapter.getId(position));
                infoToPass.putString("textMessage", arrayChat.get(position));


                if (isTablet || orientation == 2){//for a tablet
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    messageFragment  =  new MessageFragment();
                    messageFragment.setArguments( infoToPass );
//                    ft.addToBackStack("Any name, not used"); //only undo FT on back button
                    ft.replace( R.id.frame , messageFragment);
                    ft.commit();

                } else {//for a phone
                    Intent phoneIntent = new Intent (ChatWindow.this, MessageDetails.class);
                    phoneIntent.putExtras(infoToPass);
                    startActivityForResult(phoneIntent, 50);
                }
            }
        });

        //SEND BUTTON
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMessage();
            }
        });

        editText.setOnKeyListener(new EditText.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if (keyCode == KeyEvent.KEYCODE_ENTER){
//                        Log.i("*******Lab 5", String.valueOf(keyCode));
                        addMessage();
                        return true;
                    }
                }
                return false;
            }
        });

        messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount() & getView()

    }//END OF ONCREATE()

    private void addMessage(){
//        Log.i("********Lab5 Message", editText.getText().toString());
        String message = editText.getText().toString();
        arrayChat.add(editText.getText().toString());
        cv.put(dbHelper.KEY_MESSAGE, message);
        db.insert(dbHelper.TABLE_NAME, "NullColumnName", cv);
//      listView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
        editText.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            if (resultCode == 50){
                //update database
                Bundle infoToPass = data.getExtras();
                long ID = (long)infoToPass.get("ID");
                Log.i("******onActivityResult", String.valueOf(ID));
                deleteMessage(ID);
            }
        }
    }
    public void deleteMessage(long id){
        db.delete(dbHelper.TABLE_NAME, dbHelper.KEY_ID +" = " + (int) id, null);
        db.execSQL("DELETE FROM " + dbHelper.TABLE_NAME + " WHERE " + dbHelper.KEY_ID + " = " + (int) id);
        Log.i("****** deleteMessage", String.valueOf(id));
        arrayChat.remove((int) id);
        if (isTablet) {
            getFragmentManager().beginTransaction().remove(messageFragment).commit();
        }
        messageAdapter.notifyDataSetChanged();

    }

    private class ChatAdapter extends ArrayAdapter<String> {
        LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
        View result;

        public ChatAdapter(Context ctx) {
            super(ctx, 0);

        }

        //NUMBER OF ITEMS IN ARRAYLIST
        public int getCount() {

            return arrayChat.size();
        }

        //GET ITEM FROM ARRAYLIST
        public String getItem(int position) {
            return arrayChat.get(position);
        }

        //CREATE VIEW OBJECT
        public View getView(int position, View convertView, ViewGroup parent) {
            //CREATE INFLATER
            //SET LAYOUT USING INFLATOR - IF POSSITION INCOMING OUTGOING
            for(String message: arrayChat){
                if(position%2 == 0)
                    result = inflater.inflate(R.layout.chat_row_incoming, null);
                else
                    result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }

            //FIND TEXTVIEW IN RESULT
            TextView message = (TextView) result.findViewById(R.id.message_text);
            //SET TEXT IN RESULT
            message.setText(getItem(position)); // get the string at position
            return result;
        }

        public long getId(int position) {
            return position;
        }
    }
    public void onResume() {
        super.onResume();
//        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    public void onStart() {
        super.onStart();
//        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    public void onPause(){
        super.onPause();
//        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    public void onStop(){
        super.onStop();
//        Log.i(ACTIVITY_NAME, "In onStop()");
    }
    public void onDestroy(){
        super.onDestroy();
        db.close();
//        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}

