package com.mibrh.thoughtfulshow.Clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class RESTClient {
    private static String TAG = "RESTClient";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final int TIME_OUT = 40000;

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(TIME_OUT);
        client.get(url, params, responseHandler);
    }
}