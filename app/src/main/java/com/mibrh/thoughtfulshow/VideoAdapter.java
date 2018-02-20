package com.mibrh.thoughtfulshow;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private List<Video> videoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, duration;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.row_video_title);
            duration = (TextView) view.findViewById(R.id.row_video_duration);
            thumbnail = (ImageView) view.findViewById(R.id.row_video_thumbnail);
        }
    }

    // remove username for original
    public VideoAdapter(List<Video> videoList) {
        this.videoList = videoList;
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
        //Message message = messageList.get(position);
        //holder.username.setText(message.getUsername());
        //holder.text.setText(message.getText());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}