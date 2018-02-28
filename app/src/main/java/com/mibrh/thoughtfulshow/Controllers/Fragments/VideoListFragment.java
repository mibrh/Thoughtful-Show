package com.mibrh.thoughtfulshow.Controllers.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Controllers.Activities.VideoActivity;
import com.mibrh.thoughtfulshow.Controllers.Adapters.RecyclerTouchListener;
import com.mibrh.thoughtfulshow.Controllers.Adapters.VideoAdapter;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import java.util.ArrayList;


public class VideoListFragment extends Fragment {
    public static final String EXTRA_MESSAGE = "VideoObject";
    private final String TAG = "VideoListFragment";
    RecyclerView recyclerViewVideos;
    ProgressBar progressBar;
    private ArrayList<Video> videoList = new ArrayList<>();
    private VideoAdapter vAdapter;

    public VideoListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vid_list, container, false);
        Context context = getContext();

        // Initialize Views
        progressBar = (ProgressBar) root.findViewById(R.id.progress_bar_main);
        recyclerViewVideos = (RecyclerView) root.findViewById(R.id.recycler_view_messages_display);

        // Initialize adapter
        vAdapter = new VideoAdapter(videoList, context);
        Log.d(TAG, "vAdapter setup");

        // Set up recycler view
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerViewVideos.setLayoutManager(mLayoutManager);
        recyclerViewVideos.setItemAnimator(new DefaultItemAnimator());
        recyclerViewVideos.setAdapter(vAdapter);
        Log.d(TAG, "Adapter attached to recycler");

        updateList();

        // RecyclerView OnItemClickListener callback
        recyclerViewVideos.addOnItemTouchListener(new RecyclerTouchListener(App.getContext(), recyclerViewVideos, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Video video = videoList.get(position);
//                Toast.makeText(getApplicationContext(), video.getTitle() + " clicked", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onClick called for: " + video.getTitle() + ", id: " + video.getId() + ", starting intent");
                // Intent to start VideoActivity
                Intent intent = new Intent(App.getContext(), VideoActivity.class);
                intent.putExtra(EXTRA_MESSAGE, video);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
                Video video = videoList.get(position);
                Toast.makeText(App.getContext(), video.getTitle() + " long clicked", Toast.LENGTH_SHORT).show();
            }
        }));
        return root;
    }

    private void updateList() {
        // callback - get video list
        YoutubeClient.getList(new YoutubeClient.OnVideosReceived() {
            @Override
            public void videoListCreated(ArrayList<Video> videos) {
                videoList.addAll(videos);
                recyclerViewVideos.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                vAdapter.notifyDataSetChanged();
                Log.d(TAG, "VideoAdapter notified on change to videoList");
            }
        });
        Log.d(TAG, "YoutubeClient.getList called and video list set");
    }
}