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
            JSONObject jsonArticle = articleJsonArray.getJSONObject(indexArticle);
            imageStringUrl = jsonArticle.getString("urlToImage");
            String stringTitle = this.getResources().getString(R.string.title) + " "
                    + jsonArticle.getString("title");
            title.setText(stringTitle);
            String stringAuthor = this.getResources().getString(R.string.author)
                    + jsonArticle.getString("author");
            author.setText(stringAuthor);
            String stringDesc = this.getResources().getString(R.string.description)
                    + jsonArticle.getString("description");
            description.setText(stringDesc);
            String stringContent = this.getResources().getString(R.string.content)
                    + jsonArticle.getString("content");
            content.setText(stringContent);
            String stringPublished = this.getResources().getString(R.string.published)
                    + jsonArticle.getString("publishedAt");
            published.setText(stringPublished);
            String stringUrl = this.getResources().getString(R.string.url)
                    + jsonArticle.getString("url");
            url.setText(stringUrl);
            String stringSource = this.getResources().getString(R.string.source)
                    + jsonArticle.getJSONObject("source").getString("name");
            source.setText(stringSource);
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
                    Log.e("", "There was an issue processing the image.");
                }
            });
        mRequestQueue.add(request);
    }
}
