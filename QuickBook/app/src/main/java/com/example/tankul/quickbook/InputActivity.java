package com.example.tankul.quickbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import db.Database;
import db.Database_Helper;

public class InputActivity extends AppCompatActivity {
    Database_Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_input);
        //Intent intent = getIntent();
        Context context = this;
        mHelper = new Database_Helper(context);
        final SQLiteDatabase db = mHelper.getWritableDatabase();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.writepage);
        final EditText text = (EditText) dialog.findViewById(R.id.TextInput);
        final Button URLButton = (Button) dialog.findViewById(R.id.URLButton);
        final Button textButton = (Button) dialog.findViewById(R.id.TextButton);
        dialog.show();
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
                setResult(Activity.RESULT_OK);
                finish();
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
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }
}
