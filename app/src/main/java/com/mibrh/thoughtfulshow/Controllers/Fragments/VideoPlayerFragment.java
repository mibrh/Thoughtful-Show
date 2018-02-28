package com.mibrh.thoughtfulshow.Controllers.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Toast;
import android.widget.VideoView;

import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Controllers.Activities.MainActivity;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

public class VideoPlayerFragment extends Fragment{
    public VideoPlayerFragment() {}
    private final String TAG = "VideoPlayerFragment";

    private String videoPath;
    private static ProgressDialog progressDialog;

    VideoView videoView;
    TextView videoTitle;
    TextView videoDescription;

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_vid_player, container, false);
        context = getContext();

        // Get the username from the intent that started this activity
        Video video = MainActivity.videoSel;

        // Initialize views
        videoView = (VideoView) root.findViewById(R.id.video_view);
        videoTitle = (TextView) root.findViewById(R.id.video_stream_title);
        videoDescription = (TextView) root.findViewById(R.id.video_stream_description);

        // Set text in description block
        videoTitle.setText(video.getTitle());
        videoDescription.setText(video.getDescription());

        // Progress Dialog
        progressDialog = ProgressDialog.show(context, "", "Buffering video", true);
        progressDialog.setCancelable(true);

        // Get videoURL
        YoutubeClient.getVideoURL(video.getId(), new YoutubeClient.OnStreamURLReceived() {
            @Override
            public void videoStreamURLReceived(String videoStreamURL) {
                videoPath = videoStreamURL;
                PlayVideo();
            }
        });

        return root;
    }

    private void PlayVideo() {
        Log.d(TAG, "PlayVideo called");
        try {
            getActivity().getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(context);
            mediaController.setAnchorView(videoView);

            Uri videoURI = Uri.parse(videoPath);
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(videoURI);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressDialog.dismiss();
                    videoView.start();
                }
            });
        } catch(Exception e) {
            progressDialog.dismiss();
            e.printStackTrace();
            Toast.makeText(context, "Error trying to play video", Toast.LENGTH_SHORT);
//            finish();
        }
    }
}