package com.example.booklend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.MyHolder> {
    private ArrayList<Book> book;
    private Context context;
    public BookItemAdapter(ArrayList<Book> book, Context context)
    {
        this.book = book;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context).load(book.get(position).getImageUri()).into(holder.iv_book_item);
        holder.tv_book_item_name.setText(book.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return book.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView tv_book_item_name;
        ImageView iv_book_item;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tv_book_item_name = itemView.findViewById(R.id.tv_book_item_name);
            iv_book_item = itemView.findViewById(R.id.iv_book_item);
        }
    }
}
