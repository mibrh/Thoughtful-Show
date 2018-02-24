package com.mibrh.thoughtfulshow.Models;


import java.util.ArrayList;

public class VideoList {
    private static ArrayList<Video> videoArrayList= new ArrayList<Video>(){{
        add(new Video("deQBVkPClH0"));
        add(new Video("PNkzTZhB7nw"));
        add(new Video("gkr__E0rI30"));
        add(new Video("uudd5WISmNQ"));
        add(new Video("X5GWfEL5jh8"));
        add(new Video("5gnCkOH3NYY"));
        add(new Video("EUK-2PUFbqo"));
        add(new Video("jXeov-EWIXE"));
        add(new Video("4GbT5KUeej0"));
        // Do not include 3sTS1E5XFd0
    }};

    public static ArrayList<Video> getAll() {
        return videoArrayList;
    }
}
