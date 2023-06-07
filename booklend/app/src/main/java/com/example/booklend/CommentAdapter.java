package com.example.booklend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    ArrayList<Comment> commentArrayList;
    Context context;

    public CommentAdapter(ArrayList<Comment> commentArrayList, Context context) {
        this.commentArrayList = commentArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return commentArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.lv_comment_item, viewGroup, false);
        }
        TextView tv_username, tv_comment, tv_comment_date;
        ImageView iv_user;

        tv_username = view.findViewById(R.id.tv_username);
        tv_comment = view.findViewById(R.id.tv_comment);
        iv_user = view.findViewById(R.id.iv_user);
        tv_comment_date = view.findViewById(R.id.tv_comment_date);

        Glide.with(context).load(commentArrayList.get(i).getUri()).circleCrop().into(iv_user);
        tv_username.setText(commentArrayList.get(i).getUsername());
        tv_comment.setText(commentArrayList.get(i).getComment());
        tv_comment_date.setText(commentArrayList.get(i).getComment_date());
        return view;
    }
}
