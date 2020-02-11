package com.example.android_asn_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private Button btnRequest;

    private RequestQueue mRequestQueue;
    private JsonObjectRequest mJsonRequest;
    private JSONObject newsResponse;
    private JSONArray articleList;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnRequest = (Button) findViewById(R.id.button);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryInput(v);
                sendAndRequestResponse();
            }
        });
    }

    private void sendAndRequestResponse() {

        //RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        JsonObjectRequest mJsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                newsResponse = response;
                Toast.makeText(getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG, "Error :" + error.toString());
            }
        });

        mRequestQueue.add(mJsonRequest);
    }

    public void queryInput(View view) {
        EditText inputText = (EditText) findViewById(R.id.inputText);
        String inputTextString = inputText.getText().toString();
        url = generateURL(inputTextString);
    }

    public String generateURL(String query) {
        return "https://newsapi.org/v2/everything?q=" + query
                + "&sortBy=publishedAt&apiKey=789a4eb72dd04d369213df40d906db11";
    }


    public void populatePage() {
        try {
            articleList = newsResponse.getJSONArray("articles");
            for (int i = 0; i < articleList.length(); i++) {
                JSONObject article = articleList.getJSONObject(i);
            }
        } catch (JSONException e) {
            Log.e("Article Conversion: ", e.toString());
        }
    }

    public void filterDate(){
        try{
            articleList = newsResponse.getJSONArray("articles");
        } catch (JSONException e) {
            Log.e("No Articles: ", e.toString());
        }


    }

}
