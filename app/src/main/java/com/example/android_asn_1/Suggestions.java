package com.example.android_asn_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Suggestions extends AppCompatActivity {

    private String newsResponse = "";
    private JSONObject jsonNewsResponse;
    private JSONArray articleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestions);
        newsResponse = (String) getIntent().getExtras().get("jsonString");
        try {
            jsonNewsResponse = new JSONObject(newsResponse);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        populatePage();
    }


    public void populatePage() {
        try {
            articleList = jsonNewsResponse.getJSONArray("articles");
            int length = articleList.length();
            List<String> test = new ArrayList<String>(length);
            for (int i = 0; i < articleList.length(); i++) {
                JSONObject article = articleList.getJSONObject(i);
                test.add(article.toString());
            }
            ListView suggestions = (ListView) findViewById(R.id.articleList);
            suggestions.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, test));
        } catch (JSONException e) {
            Log.e("Article Conversion: ", e.toString());
        }
    }

}
