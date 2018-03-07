package com.mibrh.thoughtfulshow.Controllers.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibrh.thoughtfulshow.Controllers.Activities.MainActivity;
import com.mibrh.thoughtfulshow.Models.Comment;
import com.mibrh.thoughtfulshow.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_VIDINFO = 0;
    private static final int TYPE_VIDCOMMENT = 1;

    private List<Comment> commentList;
    private Context context;

    class VHComment extends RecyclerView.ViewHolder {

        private ImageView userimage;
        private TextView username;
        private TextView text;

        public VHComment(View view) {
            super(view);
            userimage = (ImageView) view.findViewById(R.id.comment_user_image);
            username = (TextView) view.findViewById(R.id.comment_user_name);
            text = (TextView) view.findViewById(R.id.comment_text);
        }
    }

    class VHInfo extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;

        public VHInfo(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.video_stream_title);
            description = (TextView) view.findViewById(R.id.video_stream_description);
        }
    }

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout;
        if(viewType == TYPE_VIDINFO) {
            layout = R.layout.video_details_video_player;
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            return new VHInfo(itemView);
        } else if(viewType == TYPE_VIDCOMMENT) {
            layout = R.layout.row_video_comment_list_item;
            View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            return new VHComment(itemView);
        }
        throw new RuntimeException("there is no viewtype matching the type " + viewType);
    }

    private Comment getComment(int position) {
        return commentList.get(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHInfo) {
            VHInfo VHinfo = (VHInfo)holder;
            VHinfo.title.setText(MainActivity.videoSel.getTitle());
            VHinfo.description.setText(MainActivity.videoSel.getDescription());
        } else if(holder instanceof VHComment) {
            Comment comment = commentList.get(position-1);
            VHComment VHcomment = (VHComment)holder;
            Picasso.with(context).load(comment.getAuthorProfileImageUrl())
                    .transform(new CropCircleTransformation())
                    .into(VHcomment.userimage);
            VHcomment.username.setText(comment.getAuthorDisplayName());
            VHcomment.text.setText(comment.getText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_VIDINFO;
        return TYPE_VIDCOMMENT;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return commentList.size() + 1;
    }

}