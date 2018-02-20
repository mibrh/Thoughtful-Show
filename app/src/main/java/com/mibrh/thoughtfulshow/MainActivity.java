package com.mibrh.thoughtfulshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity:";
    RecyclerView recyclerViewVideos;
    ArrayList<Video> videoList = VideoList.getAll();
    VideoAdapter vAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewVideos = (RecyclerView) findViewById(R.id.recycler_view_messages_display);
            Log.d(TAG, "Set up recyclerViewVideos");
        // fetchData for videoList
        for (Video video : videoList){
            video.fetchData();
        }
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
    }


}
