package com.mibrh.thoughtfulshow;


import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public class App {
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
}
