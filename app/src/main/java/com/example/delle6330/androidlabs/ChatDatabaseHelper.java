package com.example.delle6330.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dell E6330 on 3/4/2018.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "CHAT_DATABASE";
    public final static Integer VERSION = 4;
    public final static String TABLE_NAME = "CHAT_TABLE";
    public final static String KEY_ID = "ID";
    public final static String KEY_MESSAGE = "MESSAGE";

    private final static String QUERY_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGE + " text);";


    ChatDatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION);
        Log.w("********Lab5", "ChatDatabaseHelper Constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE);
        Log.w("********Lab5", "DatabaseHelper onCreate()");
   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE CHAT_TABLE;");
        Log.w("********Lab5", "DatabaseHelper onUpgrade: oldVersion=" + i + " newVersion=" + i1);
        onCreate(db);
    }

}
