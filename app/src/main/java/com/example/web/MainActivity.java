package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText outputText;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputText = findViewById(R.id.editTextTextMultiLine);
        inputText = findViewById(R.id.textInputEditText);
        @SuppressLint("StaticFieldLeak")
        class Download extends AsyncTask<String, Void, Bitmap> {
//                    Thread thread = new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            ;
//
//                        }
//                    });
//                    thread.start();

            @Override
            protected Bitmap doInBackground(String... strings) {
                String urlText = inputText.getText().toString();
                if (!urlText.equals("")) {
                    try {
                        URL url = new URL("http://" + urlText);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        try {
                            InputStream inputStream = connection.getInputStream();
                            String text = "";
                            String line = "" + inputStream.read();
                            while (line != "0") {
                                text += line;
                            }

                            outputText.setText(text);
                        } finally {
                            connection.disconnect();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }

//            @Override
//            protected void onPostExecute(Bitmap bitmap) {
//                super.onPostExecute(bitmap);
//
//            }
        }
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download download = new Download();
                download.doInBackground();
            }
        });


    }

}