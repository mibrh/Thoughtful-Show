package com.mibrh.thoughtfulshow.Controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    RecyclerView recyclerViewVideos;
    ArrayList<Video> videoList = new ArrayList<>();
    VideoAdapter vAdapter;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.setContext(getApplicationContext());

        recyclerViewVideos = (RecyclerView) findViewById(R.id.recycler_view_messages_display);
        Log.d(TAG, "Set up recyclerViewVideos");

        // Initialize vars
        vAdapter = new VideoAdapter(videoList, this);
        Log.d(TAG, "vAdapter setup");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Log.d(TAG, "New LayoutManager for RecyclerView");

        recyclerViewVideos.setLayoutManager(mLayoutManager);
        Log.d(TAG, "Set LayoutManager to recyclerViewVideos");

        recyclerViewVideos.setItemAnimator(new DefaultItemAnimator());
        Log.d(TAG, "Set default animator for recyclerViewVideos");

        recyclerViewVideos.setAdapter(vAdapter);
        Log.d(TAG, "Set adapter for recyclerViewVideos");
//        videoList.get(1).fetchData();

        YoutubeClient.getList();
        Log.d(TAG, "YoutuveClient.getList called");

        videoList = YoutubeClient.getVideoArrayList();
        Log.d(TAG, "YoutubeCLient.getVideoArrayList called");
    }
}
