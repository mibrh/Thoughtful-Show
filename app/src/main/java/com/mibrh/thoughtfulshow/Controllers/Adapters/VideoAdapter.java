package com.mibrh.thoughtfulshow.Controllers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibrh.thoughtfulshow.R;
import com.mibrh.thoughtfulshow.Models.Video;
import com.squareup.picasso.Picasso;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private List<Video> videoList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        // TODO:
        // Get duration value from JSON object
        private TextView title, duration;
        private ImageView thumbnail;

        private MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.row_video_title);
            duration = (TextView) view.findViewById(R.id.row_video_duration);
            thumbnail = (ImageView) view.findViewById(R.id.row_video_thumbnail);
        }
    }

    // remove username for original
    public VideoAdapter(List<Video> videoList, Context context) {
        this.videoList = videoList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;

        layout = R.layout.row_video_list_item;

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Video video = videoList.get(position);
        Picasso.with(context).load(video.getThumbnailURL()).into(holder.thumbnail);
        holder.title.setText(video.getTitle());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}