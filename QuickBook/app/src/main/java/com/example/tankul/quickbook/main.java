package com.example.tankul.quickbook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import db.Database;
import db.Database_Helper;


public class main extends AppCompatActivity {
    ArrayList<String> textsandURLs = new ArrayList<>();
    String ToShow;

    Database_Helper mHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new Database_Helper(this);
        db = mHelper.getReadableDatabase();
        cursor = db.query(Database.Book.TABLE,
                new String[] {Database.Book._ID,
                        Database.Book.PAGE_CONTENTS,
                        Database.Book.TEXT_TYPE},
                null, null, null, null, null);
        final Intent intent = new Intent(this, InputActivity.class);
            if (cursor.getCount()<1) {
                setFirstPage(intent);
            }
            cursor.moveToNext();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                String num = "" + cursor.getCount();
                Log.d("", num);
                updateUI();
            }
        });
    }

    public void setFirstPage(Intent intent) {
        Toast.makeText(this, "Please input your first text or URL.",
                Toast.LENGTH_SHORT).show();
        startActivity(intent);
        int num = cursor.getCount();
        String ah = "" + num;
        Log.d("",ah);
    }

    public void nextButton(View v) {
        if (cursor.isLast())
            Log.d("Cursor is", "at last");
        else
            cursor.moveToNext();
        String num = "" + cursor.getCount();
        Log.d("", num);
        updateUI();
    }

    public void prevButton(View v) {
        if (cursor.isFirst())
            Log.d("Cursor is", "at first");
        else
            cursor.moveToPrevious();
        String num = "" + cursor.getCount();
        Log.d("", num);
        updateUI();
    }

    private void updateUI() {
        TextView textview = (TextView) findViewById(R.id.PlainTextView);
        WebView webview = (WebView) findViewById(R.id.WebPageView);

        if (cursor.getInt(2) == 1)
        {
            webview.loadUrl(cursor.getString(1));
            textview.setText(null);
        }
        else
        {
            textview.setText(cursor.getString(1));
        }
    }
}