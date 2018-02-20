package com.mibrh.thoughtfulshow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewVideos;
    List<Video> videoList;
    VideoAdapter vAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewVideos = (RecyclerView) findViewById(R.id.recycler_view_messages_display);

        // Initialize vars
        vAdapter = new VideoAdapter(videoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewVideos.setLayoutManager(mLayoutManager);
        recyclerViewVideos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVideos.setAdapter(vAdapter);

    }


}
