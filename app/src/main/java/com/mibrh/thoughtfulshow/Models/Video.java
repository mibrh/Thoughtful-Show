package com.mibrh.thoughtfulshow.Models;

import org.json.JSONException;
import org.json.JSONObject;

public class Video {
    private final String TAG = "Video";
    private String id;
    private String title;
    private String description;


    public static Video deserialize(JSONObject jsonObject) throws JSONException {
        Video video = new Video();
        JSONObject snippet = jsonObject.getJSONObject("snippet");
        video.id = jsonObject.getJSONObject("id").getString("videoId");
        video.title = snippet.getString("title");
        video.description = snippet.getString("description");
        return video;
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public String getThumbnailURL() {
        return "https://img.youtube.com/vi/" + id + "/hqdefault.jpg";
    }
}
