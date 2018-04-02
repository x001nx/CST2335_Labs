package com.example.delle6330.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class TestToolbar extends AppCompatActivity {
    Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Lab8 - Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu m) {
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        Log.i("****** Lab8 Id", String.valueOf(mi.getItemId()));

            switch (id) {
                case R.id.action_one:
                    Log.i("****** Lab8", "First Menu Selected");
//                    Snackbar.make(this, "Lab8 - Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    break;
                case R.id.action_two:
                    Log.i("****** Lab8", "Second Menu Selected");
                    showDialog();
                    break;
                case R.id.action_three:
                    Log.i("******Lab8", "Third Menu Selected");
                    break;
                case R.id.about_item:
                    Toast.makeText(this, "Version 1.0, by Pavel Jilinski", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.i("****** Lab8", "Something else");
                    break;
            }
            return true;
        }

        public void showDialog(){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.dialog_lab8);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Log.i("******* Lab8 Dialog", "OK");
                    finishActivity(1);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            // Create the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }

    }
