package com.example.booklendadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    ArrayList<User> userArrayList;
    Context context;

    public UserAdapter(ArrayList<User> userArrayList, Context context) {
        this.userArrayList = userArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.lv_user_item, viewGroup, false);
        }
        ImageView iv_user;
        TextView tv_username, tv_uid;

        iv_user = view.findViewById(R.id.iv_user_avatar);
        tv_username = view.findViewById(R.id.tv_user_name);
        tv_uid = view.findViewById(R.id.tv_uid);

        Glide.with(context).load(userArrayList.get(i).getUri()).circleCrop().into(iv_user);
        tv_username.setText(userArrayList.get(i).getUsername());
        tv_uid.setText(userArrayList.get(i).getUid());
        return view;
    }
}
