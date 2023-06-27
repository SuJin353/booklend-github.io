package com.example.booklendadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {
    ArrayList<Book> bookArrayList;
    Context context;

    public BookAdapter(ArrayList<Book> bookArrayList, Context context) {
        this.bookArrayList = bookArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bookArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return bookArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.lv_book_item, viewGroup, false);
        }
        ImageView iv_book_cover;
        TextView tv_id_info, tv_book_name_info;
        iv_book_cover = view.findViewById(R.id.iv_book_cover);
        tv_id_info = view.findViewById(R.id.tv_id_info);
        tv_book_name_info = view.findViewById(R.id.tv_book_name_info);

        Glide.with(view.getContext()).load(bookArrayList.get(i).getImageUri()).into(iv_book_cover);
        tv_id_info.setText(bookArrayList.get(i).getKey());
        tv_book_name_info.setText(bookArrayList.get(i).getName());
        return view;
    }
}
