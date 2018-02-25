package com.mibrh.thoughtfulshow.Controllers;


import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.mibrh.thoughtfulshow.Clients.YoutubeClient;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

public class VideoActivity extends AppCompatActivity{
    private final String TAG = "VideoActivity";

    private String videoPath;
    private static ProgressDialog progressDialog;

    VideoView videoView;
    TextView videoTitle;
    TextView videoDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.stream_video);

        // Get the username from the intent that started this activity
        Video video = (Video) getIntent().getSerializableExtra(MainActivity.EXTRA_MESSAGE);
        Log.d(TAG, "Got video from Intent.getSerializableExtra, id is: " + video.getId());

        // Initialize views
        videoView = (VideoView) findViewById(R.id.video_view);
        videoTitle = (TextView) findViewById(R.id.video_stream_title);
        videoDescription = (TextView) findViewById(R.id.video_stream_description);

        // Set text in description block
        videoTitle.setText(video.getTitle());
        videoDescription.setText(video.getDescription());

        // Progress Dialog
        progressDialog = ProgressDialog.show(VideoActivity.this, "", "Buffering video", true);
        progressDialog.setCancelable(true);

        // Get videoURL
        YoutubeClient.getVideoURL(video.getId(), new YoutubeClient.OnStreamURLReceived() {
            @Override
            public void videoStreamURLReceived(String videoStreamURL) {
                videoPath = videoStreamURL;
                PlayVideo();
            }
        });
    }

    private void PlayVideo() {
        Log.d(TAG, "PlayVideo called");
        try {
            getWindow().setFormat(PixelFormat.TRANSLUCENT);
            MediaController mediaController = new MediaController(VideoActivity.this);
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
            Toast.makeText(VideoActivity.this, "Error trying to play video", Toast.LENGTH_SHORT);
            finish();
        }
    }
}
