package com.example.tankul.quickbook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import db.Database_Helper;


public class main extends AppCompatActivity {
    int currentPage = 0;
    ArrayList<String> textsandURLs = new ArrayList<>();
    String ToShow;

    Database_Helper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelper = new Database_Helper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Intent intent = new Intent(this, InputActivity.class);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
                updateUI();
            }
        });
    }

    public void nextButton(View v) {
        if (currentPage < textsandURLs.size() - 1) {
            {
                currentPage++;
                updateUI();
            }
        }
    }

    public void prevButton(View v) {
        if (currentPage > 0) {
            {
                currentPage--;
                updateUI();
            }
        }
    }

    private void updateUI() {
        TextView textview = (TextView) findViewById(R.id.PlainTextView);
        WebView webview = (WebView) findViewById(R.id.WebPageView);

        if (!textsandURLs.isEmpty() && currentPage >= 0 && currentPage < textsandURLs.size()) {
            if (textsandURLs.get(currentPage).contains("~URL:~")) {
                textview.setText(null);
                webview.loadUrl(textsandURLs.get(currentPage).substring(6));
            }
            else {
                textview.setText(textsandURLs.get(currentPage));
            }
        }
    }
}