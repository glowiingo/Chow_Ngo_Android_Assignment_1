package com.example.android_asn_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();
    private JSONObject newsResponse;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRequest = (Button) findViewById(R.id.button);

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
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        JsonObjectRequest mJsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                newsResponse = response;
                //display the response on screen
                Intent intentSuggestions = new Intent(MainActivity.this, Suggestions.class);
                intentSuggestions.putExtra("jsonString", newsResponse.toString());
                startActivity(intentSuggestions);
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
        inputTextString = inputTextString.replace(" ", this.getResources().getString(R.string.replace_space));
        String date = getWeekAgo();
        url = generateURL(inputTextString, date);
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return simpleDateFormat.format(date);
    }

    public String getWeekAgo() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return simpleDateFormat.format(date);
    }


    public String generateURL(String query, String date) {
        return this.getResources().getString(R.string.news_api_https) + query
                + this.getResources().getString(R.string.from_query) + date
                + this.getResources().getString(R.string.api_query);
    }


}
