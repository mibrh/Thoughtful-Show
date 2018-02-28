package com.mibrh.thoughtfulshow.Controllers.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.mibrh.thoughtfulshow.App;
import com.mibrh.thoughtfulshow.Controllers.Fragments.VideoListFragment;
import com.mibrh.thoughtfulshow.R;


public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "MainActivity onCreate");
        super.onCreate(savedInstanceState);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // setContentView now to avoid crash
        setContentView(R.layout.activity_main);
        App.setContext(getApplicationContext());

        // Populate VideoListFragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new VideoListFragment()).commit();
    }


}
