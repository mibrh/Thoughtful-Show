package com.mibrh.thoughtfulshow.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "VideoObject";
    private final String TAG = "MainActivity";
    RecyclerView recyclerViewVideos;
    ProgressBar progressBar;
    ArrayList<Video> videoList = new ArrayList<>();
    VideoAdapter vAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // setContentView now to avoid crash
        setContentView(R.layout.activity_main);

        App.setContext(getApplicationContext());

        progressBar = (ProgressBar) findViewById(R.id.progress_bar_main);

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

        // callback - get video list
        YoutubeClient.getList(new YoutubeClient.OnVideosReceived(){
            @Override
            public void videoListCreated(ArrayList<Video> videos) {
                // TODO:
                // Remove try catch block, add loading circle while adapter updates
                videoList.addAll(videos);
                progressBar.setVisibility(View.GONE);
                recyclerViewVideos.setVisibility(View.VISIBLE);
                vAdapter.notifyDataSetChanged();
                Log.d(TAG, "VideoAdapter notified on change to videoList");
            }
        });
        Log.d(TAG, "YoutubeClient.getList called and video list set");

        // RecyclerView OnItemClickListener callback
        recyclerViewVideos.addOnItemTouchListener(new RecyclerTouchListener(App.getContext(), recyclerViewVideos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Video video = videoList.get(position);
                Toast.makeText(getApplicationContext(), video.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
                // Intent to start VideoActivity
                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.putExtra(EXTRA_MESSAGE, video);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Video video = videoList.get(position);
                Toast.makeText(getApplicationContext(), video.getTitle() + " long clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }


}
