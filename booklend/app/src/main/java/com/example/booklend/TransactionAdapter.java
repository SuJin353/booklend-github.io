package com.example.booklend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;


public class TransactionAdapter extends BaseAdapter {
    ArrayList<TransactionInfo> transactionInfoArrayList;
    Context context;
    public TransactionAdapter(Context context, ArrayList<TransactionInfo> transactionInfoArrayList)
    {
        this.context = context;
        this.transactionInfoArrayList = transactionInfoArrayList;
    }

    @Override
    public int getCount() {
        return transactionInfoArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.lv_history_item, viewGroup, false);
        }
        TextView tv_key, tv_book_name, tv_transaction_date, tv_price;

        tv_key = view.findViewById(R.id.tv_key);
        tv_book_name = view.findViewById(R.id.tv_book_name);
        tv_transaction_date = view.findViewById(R.id.tv_transaction_date);
        tv_price = view.findViewById(R.id.tv_price);

        tv_key.setText(transactionInfoArrayList.get(i).getKey());
        tv_book_name.setText(transactionInfoArrayList.get(i).getName());
        tv_price.setText("-" + transactionInfoArrayList.get(i).getPrice());
        tv_price.setTextColor(ContextCompat.getColor(context,R.color.red));
        tv_transaction_date.setText(transactionInfoArrayList.get(i).getBorrowedDateAndTime());
        return view;
    }
}
