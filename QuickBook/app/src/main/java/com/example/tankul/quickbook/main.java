package com.example.tankul.quickbook;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
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
        cursor.moveToFirst();
        updateUI();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivityForResult(intent, 1);

                cursor.moveToLast();
                updateUI();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                cursor.close();
                db.close();
                db = mHelper.getReadableDatabase();
                cursor = db.query(Database.Book.TABLE,
                        new String[] {Database.Book._ID,
                                Database.Book.PAGE_CONTENTS,
                                Database.Book.TEXT_TYPE},
                        null, null, null, null, null);
                cursor.moveToLast();
                updateUI();
            }
            else
            {
                cursor.moveToLast();
                updateUI();
            }
        }
    }

    public void setFirstPage(Intent intent) {
        Toast.makeText(this, "Please input your first text or URL.",
                Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void nextButton(View v) {
        if (!cursor.isLast())
            cursor.moveToNext();
        updateUI();
    }

    public void prevButton(View v) {
        if (!cursor.isFirst())
            cursor.moveToPrevious();
        updateUI();
    }

    public void nextTenButton(View v) {
        int i = 10;
        while (!cursor.isLast() && i != 0)
        {
            cursor.moveToNext();
            i--;
        }
        updateUI();
    }

    public void prevTenButton(View v) {

        int i = 10;
        while (!cursor.isFirst() && i != 0)
        {
            cursor.moveToPrevious();
            i--;
        }
        updateUI();
    }

    public void tapURL(View view)
    {
        WebView webview = (WebView) findViewById(R.id.WebPageView);
        if (cursor.getInt(2) == 1)
        {
            webview.loadUrl(cursor.getString(1));
        }
    }

    private void updateUI() {
        TextView textview = (TextView) findViewById(R.id.PlainTextView);

        if (cursor.getInt(2) == 1)
        {
            textview.setText(cursor.getString(1));
            textview.setTextColor(Color.BLUE);
        }
        else
        {
            textview.setTextColor(Color.GRAY);
            textview.setText(cursor.getString(1));
        }
    }
}