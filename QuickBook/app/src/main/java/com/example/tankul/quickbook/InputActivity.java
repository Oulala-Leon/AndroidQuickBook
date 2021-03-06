package com.example.tankul.quickbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import db.Database;
import db.Database_Helper;

public class InputActivity extends AppCompatActivity {
    Database_Helper mHelper;
    SQLiteDatabase db;
    Dialog dialog;
    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        Context context = this;
        mHelper = new Database_Helper(context);
        db = mHelper.getWritableDatabase();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_input);
        text = (EditText) dialog.findViewById(R.id.TextInput);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

    }

    public void clickURL(View v) {
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

    public void clickText(View v) {
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
}
