package com.example.booklendadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    private ArrayList<Book> bookArrayList;
    private Context context;

    public GridViewAdapter(ArrayList<Book> bookArrayList, Context context) {
        this.bookArrayList = bookArrayList;
        this.context = context;
    }
    @Override
    public int getCount() {
        return bookArrayList.size();
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
            view = LayoutInflater.from(context).inflate(R.layout.rv_children, viewGroup, false);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_child_item);
        Glide.with(context).load(bookArrayList.get(i).getImageUri()).into(imageView);
        return view;
    }
}
