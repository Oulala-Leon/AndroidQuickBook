package com.example.tankul.quickbook;

import android.app.Dialog;
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


public class main extends AppCompatActivity {
    int currentPage = 0;
    ArrayList<String> textsandURLs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(main.this);
                dialog.setContentView(R.layout.writepage);
                dialog.show();
                final EditText text = (EditText) dialog.findViewById(R.id.TextInput);
                final Button URLButton = (Button) dialog.findViewById(R.id.URLButton);
                final Button textButton = (Button) dialog.findViewById(R.id.TextButton);
                textButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textsandURLs.add(text.getText().toString());
                        currentPage = textsandURLs.size() - 1;
                        updateUI();
                        dialog.dismiss();
                    }
                });

                URLButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textsandURLs.add("~URL:~" + (text.getText().toString()));
                        currentPage = textsandURLs.size() - 1;
                        updateUI();
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void nextButton(View v) {
        if (currentPage < textsandURLs.size()) {
            {
                currentPage++;
                updateUI();
            }
        }
    }

    public void prevButton(View v) {
        if (currentPage >= 0) {
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
            } else {
                textview.setText(textsandURLs.get(currentPage));
                //webview.destroy();
            }
        }
    }
}