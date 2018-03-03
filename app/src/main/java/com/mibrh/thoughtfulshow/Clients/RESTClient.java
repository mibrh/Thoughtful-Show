package com.mibrh.thoughtfulshow.Clients;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;


class RESTClient {
    private static String TAG = "RESTClient";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static final int TIME_OUT = 10000;

    static RequestHandle get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(TIME_OUT);
        return client.get(url, params, responseHandler);
    }
}