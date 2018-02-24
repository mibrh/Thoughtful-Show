package com.mibrh.thoughtfulshow.Clients;


import android.util.Log;

import static android.content.ContentValues.TAG;

public class YoutubeClient {

    private static final String BASE_URL = "https://gdata.youtube.com/feeds/api/videos/?v=2&alt=json";
    // "http://kholo.pk/api/nuclearn/video/"

    private static String getAbsoluteUrl(String relativeUrl) {
        Log.d(TAG, "started getAbsoluteURL with input: " + relativeUrl);
        String result = new StringBuilder(BASE_URL).insert(BASE_URL.length() - 13, relativeUrl).toString();
        Log.d(TAG, "new string is: " + result);
        return result;

    }
}