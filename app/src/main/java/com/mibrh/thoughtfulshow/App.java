package com.mibrh.thoughtfulshow;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App {
    private static String TAG = "App";
    private static Context context;

    public static void setContext(Context cntext) {
        context = cntext;
    }

    public static Context getContext() {
        return context;
    }

    // To access resources directly from any class
    public static Resources getResources() {
        return context.getResources();
    }

    public static void showToast(String string){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static void initFonts() {
        Log.d(TAG, "initfonts run");
        String font = getResources().getString(R.string.font_main_regular);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(font)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
