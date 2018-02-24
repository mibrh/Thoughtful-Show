package com.mibrh.thoughtfulshow.Clients;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.ContentValues.TAG;


public class YoutubeClient {
    private static final String CHANNEL_ID = "UCQ97Z8xFj6BAUFcjP71zNNg";
    private static final String INFO_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&maxResults=20&type=video";
    private static final String STREAM_URL = "http://kholo.pk/api/nuclearn/video/";

    public static void getList(final OnVideosReceived callback) {
        RESTClient.get(channelURL(), null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<Video> videos = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++){
                        Video video = Video.deserialize(jsonArray.getJSONObject(i));
                        videos.add(video);
                        Log.d(TAG, "parsed: " + video.getTitle());
                    }
                    Log.d(TAG, "Total items in json array: " + jsonArray.length());
                    callback.videoListCreated(videos);
                } catch(JSONException e) {
                    Log.d(TAG, "JSONException");
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error)  {
                Log.d(TAG, "onFailure called: " + statusCode);
                App.showToast("Failure getting videos from youtube");
                throwable.printStackTrace();
            }
        });
    }

    private static String getStreamURL(String videoID) {
        // TODO:
        // this gets link to JSONObject containing stream url
        return STREAM_URL + videoID;
    }

    private static String channelURL(){
        return INFO_URL + "&channelId=" + CHANNEL_ID + "&key=" + App.getResources().getString(R.string.youtube_api_key);
    }

    public interface OnVideosReceived {
        void videoListCreated(ArrayList<Video> videos);
    }
}