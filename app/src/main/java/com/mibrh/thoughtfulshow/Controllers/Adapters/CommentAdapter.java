package com.mibrh.thoughtfulshow.Controllers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibrh.thoughtfulshow.Models.Comment;
import com.mibrh.thoughtfulshow.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    private List<Comment> commentList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView userimage;
        private TextView username;
        private TextView text;

        public MyViewHolder(View view) {
            super(view);
            userimage = (ImageView) view.findViewById(R.id.comment_user_image);
            username = (TextView) view.findViewById(R.id.comment_user_name);
            text = (TextView) view.findViewById(R.id.comment_text);
        }
    }

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;

        layout = R.layout.row_video_comment_list_item;

        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        Picasso.with(context).load(comment.getAuthorProfileImageUrl()).into(holder.userimage);
        holder.username.setText(comment.getAuthorDisplayName());
        holder.text.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}