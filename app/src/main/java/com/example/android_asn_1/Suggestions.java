package com.example.android_asn_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Suggestions extends AppCompatActivity {

    // private String newsResponse = "";
    private JSONObject jsonNewsResponse;
    private int listLength = 0;
    // private JSONArray articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        String newsResponse = "";
        newsResponse = (String) getIntent().getExtras().get("jsonString");
        try {
            jsonNewsResponse = new JSONObject(newsResponse);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        populatePage();

        ListView articleList = findViewById(R.id.articleList);
        final String response = newsResponse;
        articleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                if (i >= 0 && i < listLength) {
                    Intent intentDetails = new Intent(Suggestions.this, Details.class);
                    intentDetails.putExtra("jsonString", response);
                    intentDetails.putExtra("index", i);
                    startActivity(intentDetails);
                }
            }
        });

    }


    public void populatePage() {
        try {
            JSONArray articleJsonArray = jsonNewsResponse.getJSONArray("articles");
            listLength = articleJsonArray.length();
            List<String> test = new ArrayList<>(listLength);
            for (int i = 0; i < articleJsonArray.length(); i++) {
                JSONObject article = articleJsonArray.getJSONObject(i);
                test.add(article.getString("title"));
            }
            ListView suggestions = findViewById(R.id.articleList);
            suggestions.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, test));
        } catch (JSONException e) {
            Log.e("Article Conversion: ", e.toString());
        }
    }

}
