//package com.mibrh.thoughtfulshow;
//
//import android.graphics.drawable.Drawable;
//
//import org.apache.commons.io.IOUtils;
//import org.json.JSONObject;
//
//import java.io.InputStream;
//import java.net.URL;
//
//public class YoutubeHelper {
//    public static String getTitle(String youtubeUrl) {
//        try {
//            if (youtubeUrl != null) {
//                URL embededURL = new URL("http://www.youtube.com/oembed?url=" +
//                        youtubeUrl + "&format=json"
//                );
//                String testDuration = new JSONObject(IOUtils.toString(embededURL)).getString("duration");
//                System.out.println(testDuration);
//                return new JSONObject(IOUtils.toString(embededURL)).getString("title");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public static Drawable GetThumbnail(String url) {
//        https://img.youtube.com/vi/4GbT5KUeej0/hqdefault.jpg
//
//        try {
//            if (!url.isEmpty()) {
//
//                InputStream inputStream = (InputStream) new URL(url).getContent();
//                Drawable drawable = Drawable.createFromStream(inputStream, "src name");
//                return drawable;
//            } else {
//                return null;
//            }
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public static String ConvertToVideoStream(String url) {
//        //http://kholo.pk/api/nuclearn/video/O51o05gfk9A
//        return "";
//    }
//
//    //https://www.youtube.com/watch?v=4GbT5KUeej0
//}
