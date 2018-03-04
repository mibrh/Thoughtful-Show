package com.mibrh.thoughtfulshow.Controllers.Fragments;


import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.loopj.android.http.RequestHandle;
import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Controllers.Activities.MainActivity;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

public class VideoPlayerFragment extends Fragment{
    public VideoPlayerFragment() {}
    private final String TAG = "VideoPlayerFragment";

    private String videoPath;

    VideoView videoView;
    TextView videoTitle;
    TextView videoDescription;

    RequestHandle handle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vid_player, container, false);

        // Get the username from the intent that started this activity
        Video video = MainActivity.videoSel;

        // Initialize views
        videoView = (VideoView) root.findViewById(R.id.video_view);
        videoTitle = (TextView) root.findViewById(R.id.video_stream_title);
        videoDescription = (TextView) root.findViewById(R.id.video_stream_description);

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

        return root;
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
}