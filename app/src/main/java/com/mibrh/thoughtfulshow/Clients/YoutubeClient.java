package com.mibrh.thoughtfulshow.Clients;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Models.Comment;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class YoutubeClient {
    private static final String TAG = "YoutubeClient";
    private static final Integer STREAM_REQUEST_TAG = 12345;

    private static final String CHANNEL_ID = "UCQ97Z8xFj6BAUFcjP71zNNg";
    private static final String INFO_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet,id&order=date&maxResults=20&type=video";
    private static final String STREAM_URL = "http://kholo.pk/api/nuclearn/video/";
    private static final String COMMENT_URL = "https://www.googleapis.com/youtube/v3/commentThreads?&textFormat=plainText&part=snippet&videoId=";

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

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error) {
                Log.d(TAG, "onFailure called in getList");
                App.showToast("Failure getting videos from youtube");
                throwable.printStackTrace();
            }
        });
    }

    public static RequestHandle getVideoURL(String videoID, final OnStreamURLReceived callback) {
        Log.d(TAG, "getVideoURL started");

        return RESTClient.get(streamURL(videoID), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                String videoStreamURL;
                try {
                    videoStreamURL = response.getJSONObject("streams").getString("source");
                    Log.d(TAG, "onSuccess getVideoURL, Stream URL is: " + videoStreamURL);
                    callback.videoStreamURLReceived(videoStreamURL);
                } catch(JSONException e) {
                    Log.d(TAG, "getVideoURL onSuccess JSONException");
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error) {
                Log.d(TAG, "onFailure called in getVideoURL");
                throwable.printStackTrace();
            }

            public void onCancel() {
                Log.d(TAG, " getVideoURL onCancel");
            }
        });
    }

    public static void getComments(String videoID, final OnCommentsReceived callback) {
        Log.d(TAG, "getComments started");

        RESTClient.get(commentURL(videoID), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                ArrayList<Comment> comments = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Comment comment = Comment.deserialize(jsonArray.getJSONObject(i));
                        // To get higher def image
                        String originalPhotoURL = comment.getAuthorProfileImageUrl();
                        String regexReplace = "s28-c-k-no-mo-rj-c0xffffff";
                        comment.setAuthorProfileImageUrl(originalPhotoURL.replaceAll(regexReplace, "s200-c-k-no-mo-rj-c0xffffff"));
                        comments.add(comment);
                    }
                    Log.d(TAG, "onSuccess getComments, Total count: " + jsonArray.length());
                    callback.videoCommentsReceived(comments);
                } catch(JSONException e) {
                    Log.d(TAG, "getComments onSuccess JSONException");
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject error) {
                Log.d(TAG, "onFailure called in getComments");
                throwable.printStackTrace();
            }
        });
    }

    private static String streamURL(String videoID) {
        Log.d(TAG, "streamURL called: " + STREAM_URL + videoID);
        return STREAM_URL + videoID;
    }

    private static String channelURL() {
        return INFO_URL + "&channelId=" + CHANNEL_ID + "&key=" + App.getResources().getString(R.string.youtube_api_key);
    }

    private static String commentURL(String vidID) {
        String key = App.getResources().getString(R.string.youtube_api_key);
        String type = "commentThread";
        String maxRes = "10";
        return COMMENT_URL + vidID + "&type=" + type + "&maxResults=" + maxRes + "&key=" + key;
    }

    public interface OnVideosReceived {
        void videoListCreated(ArrayList<Video> videos);
    }

    public interface OnStreamURLReceived {
        void videoStreamURLReceived(String videoStreamURL);
    }

    public interface OnCommentsReceived {
        void videoCommentsReceived(ArrayList<Comment> comments);
    }
}