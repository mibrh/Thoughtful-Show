package com.mibrh.thoughtfulshow;

public class Video {
    private String id;
    private String title;

    public Video(String id) {
        this.id = id;

    }

    public void fetchData() {
        // TODO:
        // implement this, update internal data
    }

    public String getTitle() { return this.title; }

    public String getThumbnailURL() {
        return "https://img.youtube.com/vi/" + id + "/hqdefault.jpg";
    }
}
