package com.example.android_asn_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Access the RequestQueue through your singleton class.



    }

    void makeAPICall () {

    }

    String generateURL (String query) {
        return "https://newsapi.org/v2/everything?q=" + query
                + "&sortBy=publishedAt&apiKey=789a4eb72dd04d369213df40d906db11";
    }


}
