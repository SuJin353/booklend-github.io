package com.example.booklendadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {
    ArrayList<Book> bookArrayList;

    public BookAdapter(ArrayList<Book> bookArrayList) {
        this.bookArrayList = bookArrayList;
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
        if (view == null) {
            view = LayoutInflater.from(view.getContext()).inflate(R.layout.lv_book_item, viewGroup, false);
        }
        ImageView iv_book_cover;
        TextView tv_book_name_info, tv_author_info, tv_id_info;
        ImageButton ibt_check;
        iv_book_cover = view.findViewById(R.id.iv_book_cover);
        tv_book_name_info = view.findViewById(R.id.tv_book_name_info);
        tv_author_info = view.findViewById(R.id.tv_author_info);
        tv_id_info = view.findViewById(R.id.tv_id_info);
        ibt_check = view.findViewById(R.id.ibt_check);

        Glide.with(view.getContext()).load(bookArrayList.get(i).getImageUri()).into(iv_book_cover);
        tv_book_name_info.setText(bookArrayList.get(i).getName());
        tv_author_info.setText(bookArrayList.get(i).getAuthor());
        tv_id_info.setText(bookArrayList.get(i).getKey());
        ibt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }
}
