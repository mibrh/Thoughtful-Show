package com.mibrh.thoughtfulshow.Models;


import org.json.JSONException;
import org.json.JSONObject;

public class Comment {
        private final String TAG = "Comment";
        private String authorDisplayName;
        private String authorProfileImageUrl;
        private String text;


        public static Comment deserialize(JSONObject jsonObject) throws JSONException {
            Comment comment = new Comment();
            JSONObject snippet = jsonObject.getJSONObject("snippet").getJSONObject("topLevelComment").getJSONObject("snippet");
            comment.authorDisplayName = snippet.getString("authorDisplayName");
            comment.authorProfileImageUrl = snippet.getString("authorProfileImageUrl");
            comment.text = snippet.getString("textOriginal");
            return comment;
        }

        public String getAuthorDisplayName() { return authorDisplayName; }

        public String getAuthorProfileImageUrl() { return authorProfileImageUrl; }

        public void setAuthorProfileImageUrl(String newURL) { this.authorProfileImageUrl = newURL; }

        public String getText() { return text; }
}
