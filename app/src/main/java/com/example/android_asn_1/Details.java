package com.example.android_asn_1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {
    private JSONObject jsonNewsResponse;
    private int indexArticle;
    private JSONObject jsonArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        String newsResponse;
        newsResponse = (String) getIntent().getExtras().get("jsonString");
        indexArticle = (int) getIntent().getExtras().get("index");
        try {
            jsonNewsResponse = new JSONObject(newsResponse);
        } catch (JSONException e) {
            Log.e("Error", e.toString());
        }
        populatePage();
    }

    public void populatePage() {
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        final ImageView thumbnail = (ImageView) findViewById(R.id.article_thumbnail);
        TextView title = (TextView) findViewById(R.id.article_title);
        TextView author = (TextView) findViewById(R.id.article_author);
        TextView description = (TextView) findViewById(R.id.article_description);
        TextView content = (TextView) findViewById(R.id.article_content);
        TextView source = (TextView) findViewById(R.id.article_source);
        TextView published = (TextView) findViewById(R.id.article_published_at);
        TextView url = (TextView) findViewById(R.id.article_url);
        String imageStringUrl = "";

        try {
            JSONArray articleJsonArray = jsonNewsResponse.getJSONArray("articles");
            jsonArticle = articleJsonArray.getJSONObject(indexArticle);
            imageStringUrl = jsonArticle.getString("urlToImage");
            title.setText(jsonArticle.getString("title"));
            author.setText(jsonArticle.getString("author"));
            description.setText(jsonArticle.getString("description"));
            content.setText(jsonArticle.getString("content"));
            published.setText(jsonArticle.getString("publishedAt"));
            url.setText(jsonArticle.getString("url"));
            source.setText(jsonArticle.getJSONObject("source").getString("name"));
        } catch (JSONException e) {
            Log.e("Article Conversion: ", e.toString());
        }
        ImageRequest request = new ImageRequest(imageStringUrl,
            new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    thumbnail.setImageBitmap(bitmap);
                }
            }, 0, 0, null, null,
            new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Log.e("", "");
                }
            });
        mRequestQueue.add(request);
    }
}
