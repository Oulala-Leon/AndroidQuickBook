package com.example.tankul.quickbook;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import db.Database;
import db.Database_Helper;

public class main extends AppCompatActivity {
    Database_Helper mHelper;
    int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LayoutInflater window = LayoutInflater.from(this);
        mHelper = new Database_Helper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //updateUI(0);
        String ah = new String();
        ah += currentPage;
        Log.d("currentpage is at", ah);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SQLiteDatabase db = mHelper.getWritableDatabase();
                final Dialog dialog = new Dialog(main.this);
                dialog.setContentView(R.layout.writepage);
                dialog.show();final EditText text = (EditText) dialog.findViewById(R.id.TextInput);
                final Button URLButton = (Button) dialog.findViewById(R.id.URLButton);
                final Button textButton = (Button) dialog.findViewById(R.id.TextButton);
                textButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor query = db.rawQuery("INSERT INTO " + Database.Book.TABLE + " (" +
                                Database.Book.PAGE_CONTENTS + ", " +
                                Database.Book.TEXT_TYPE + ") " +
                                " VALUES ('" + text.getText().toString() + "', '" +
                                0 + "')", null);
                        query.moveToNext();
                        db.close();
                        dialog.dismiss();
                    }
                });

                URLButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor query = db.rawQuery("INSERT INTO " + Database.Book.TABLE + " (" +
                                Database.Book.PAGE_CONTENTS + ", " +
                                Database.Book.TEXT_TYPE + ") " +
                                " VALUES ('" + text.getText().toString() + "', '" +
                                1 + "')", null);
                        query.moveToNext();
                        db.close();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void nextButton(View v)
    {
        currentPage++;
        Log.d("ah", "beh");
        updateUI(1);
    }

    public void prevButton(View v)
    {
        currentPage--;
        updateUI(-1);
    }

    private void updateUI(int direction) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor;
        String [] string_db = {Database.Book.PAGE_CONTENTS,
        Database.Book.TEXT_TYPE};
        setContentView(R.layout.activity_main);

        //cursor = db.rawQuery("SELECT " + Database.Book.PAGE_CONTENTS + " FROM " + Database.Book.TABLE + " WHERE " +
        //        Database.Book._ID + " " + currentPage + ";", null);
        cursor = db.query(Database.Book.TABLE,
                new String[] {"*"},
                null, null, null, null, null, null);
        TextView textview = (TextView)findViewById(R.id.PlainTextView);
        WebView webview = (WebView)findViewById(R.id.WebPageView);

        if (cursor != null)
        {
            if (direction == 1) {cursor.moveToNext();}
            else if (direction == -1) {cursor.moveToPrevious();}

            String type = cursor.getString(cursor.getColumnIndex(Database.Book.TEXT_TYPE));
            String text = cursor.getString(cursor.getColumnIndex(Database.Book.PAGE_CONTENTS));
            if (type == "0")
            {
                webview.destroy();
                textview.setText(text);
            }
            else
            {
                textview.setText(null);
                webview.loadUrl(text);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
