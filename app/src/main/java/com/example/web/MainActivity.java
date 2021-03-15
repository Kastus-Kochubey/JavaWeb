package com.example.web;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView outputText;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        outputText = findViewById(R.id.textView);
        inputText = findViewById(R.id.textInputEditText);
        @SuppressLint("StaticFieldLeak")
        class Download extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                String urlText = strings[0];
                if (!urlText.equals("")) {
                    try {
//                        URL url = new URL("http://" + urlText);
                        URL url = new URL("http://www.listchallenges.com/200-most-famous-people-of-all-time/vote");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        String text = "";
                        BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        while ((line = rd.readLine()) != null) {
                            text += line;
                        }
                        rd.close();
                        connection.disconnect();
                        return text;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                return null;
            }
        }
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Download download = new Download();
                String in = inputText.getText().toString();

                String str = null;
                try {
                    str = download.execute(in).get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<String> arrayList = new ArrayList<String>();
                Log.e("string", (str != null) ? str : "nope");
                /// <img class="" onerror="ImageLoadError(this)" data-src="/f/items/6574cdb5-ccf2-48ab-baf7-f76054677462.jpg" src="/f/items/6574cdb5-ccf2-48ab-baf7-f76054677462.jpg">
                Pattern pattern = Pattern.compile("<img class=\"\" onerror=\"ImageLoadError(this)\" data-src=\"(.*?)\">");
                Matcher matcher = pattern.matcher((str != null) ? str : "");
                while (matcher.find()) {
                    arrayList.add(matcher.group());
                }
//                for (int i = 0; i < 5; i++) {
//                    arrayList.add(matcher.group());
//                }
                outputText.setText(arrayList.toString());
            }
        });


    }

}