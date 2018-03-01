package com.mibrh.thoughtfulshow.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Controllers.Fragments.VideoListFragment;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    public static Video videoSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(getApplicationContext());

        // Populate VideoListFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoListFragment()).commit();
    }
}
