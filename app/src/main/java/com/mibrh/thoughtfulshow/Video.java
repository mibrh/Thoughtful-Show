package com.mibrh.thoughtfulshow;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Video {
    private final String TAG = "class/Video:";
    private String id;
    private String title;

    public Video(String id) {
        this.id = id;
        this.title = "";
    }

    public void fetchData() {
        Log.d(TAG, "fetchData called");
        // TODO:
        // implement this, update internal data
        YoutubeClient.get(id, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d(TAG, "Success");
                String title = "blah";
                try {
                    title = response.getString("title");
                    Log.d(TAG, "trying to get title");
                } catch(JSONException e) {
                    Log.d(TAG, "JSONException");
                }

                // Do something with the response
                Log.d(TAG, title);
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error)  {
                Log.d(TAG, "onFailure called: " + statusCode);
            }
        });
    }

    public String getTitle() { return title; }

    public String getThumbnailURL() {
        return "https://img.youtube.com/vi/" + id + "/hqdefault.jpg";
    }
}
