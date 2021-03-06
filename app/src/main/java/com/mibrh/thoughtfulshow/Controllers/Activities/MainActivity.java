package com.mibrh.thoughtfulshow.Controllers.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Controllers.Fragments.VideoListFragment;
import com.mibrh.thoughtfulshow.Models.Video;
import com.mibrh.thoughtfulshow.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    public static Video videoSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        App.setContext(getApplicationContext());
        App.initFonts();

        // Populate VideoListFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoListFragment()).commit();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d(TAG, "attachBaseContext called");
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
