package com.mibrh.thoughtfulshow.Controllers.Fragments;


import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.loopj.android.http.RequestHandle;
import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Controllers.Activities.MainActivity;
import com.mibrh.thoughtfulshow.Controllers.Adapters.CommentAdapter;
import com.mibrh.thoughtfulshow.Models.Comment;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import java.util.ArrayList;

public class VideoPlayerFragment extends Fragment{
    public VideoPlayerFragment() {}
    private final String TAG = "VideoPlayerFragment";

    private String videoPath;

    VideoView videoView;
    TextView videoTitle;
    TextView videoDescription;
    ProgressBar loader;

    RecyclerView recyclerViewComments;
    ProgressBar commentLoader;

    private ArrayList<Comment> commentList;
    private CommentAdapter cAdapter;

    RequestHandle handle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vid_player, container, false);

        // Get the username from the intent that started this activity
        Video video = MainActivity.videoSel;

        // Initialize Vars
        commentList = new ArrayList<>();

        // Initialize views
        videoView = (VideoView) root.findViewById(R.id.video_view);
        videoTitle = (TextView) root.findViewById(R.id.video_stream_title);
        videoDescription = (TextView) root.findViewById(R.id.video_stream_description);
        loader = (ProgressBar) root.findViewById(R.id.video_player_loader);
        commentLoader = (ProgressBar) root.findViewById(R.id.comment_list_loader);
        recyclerViewComments = (RecyclerView) root.findViewById(R.id.recycler_view_comments_display);

        showLoaderVid(true);
        showLoaderComm(true);

        // Initialize adapter
        cAdapter = new CommentAdapter(commentList, getContext());
        Log.d(TAG, "CommentAdapter setup");

        // Set text in description block
        videoTitle.setText(video.getTitle());
        videoDescription.setText(video.getDescription());

        // Get videoURL
        handle = YoutubeClient.getVideoURL(video.getId(), new YoutubeClient.OnStreamURLReceived() {
            @Override
            public void videoStreamURLReceived(String videoStreamURL) {
                Log.d(TAG, "videoStreamReceived");
                videoPath = videoStreamURL;
                Log.d(TAG, "videoPath set to: " + videoPath);
                playVideo();
            }
        });

        // Set up recycler view
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewComments.setLayoutManager(mLayoutManager);
        recyclerViewComments.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerViewComments.setAdapter(cAdapter);
        Log.d(TAG, "Adapter attached to recycler for comments");

        loadComments(video.getId());

        return root;
    }

    private void loadComments(String videoID) {
        // callback - get comment list
        YoutubeClient.getComments(videoID, new YoutubeClient.OnCommentsReceived() {
            @Override
            public void videoCommentsReceived(ArrayList<Comment> comments) {
                commentList.addAll(comments);
                cAdapter.notifyDataSetChanged();
                showLoaderComm(false);
                Log.d(TAG, "CommentAdapter notified on change to commentList");
            }
        });
        Log.d(TAG, "YoutubeClient.getComments called and completed");
    }

    private void playVideo() {
        Log.d(TAG, "playVideo called");
        try {
            getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(getContext());
            mediaController.setAnchorView(videoView);

            Uri videoURI = Uri.parse(videoPath);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoURI);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    showLoaderVid(false);
                    videoView.start();
                }
            });
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        handle.cancel(true);
        super.onDetach();
    }

    private void showLoaderVid(boolean show){
        if (show){
            loader.setVisibility(View.VISIBLE);
        } else {
            loader.setVisibility(View.GONE);
        }
    }

    private void showLoaderComm(boolean show) {
        if (show) {
            recyclerViewComments.setVisibility(View.GONE);
            commentLoader.setVisibility(View.VISIBLE);
        } else {
            recyclerViewComments.setVisibility(View.VISIBLE);
            commentLoader.setVisibility(View.GONE);
        }
    }
}