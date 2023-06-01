package com.example.booklend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder>{

    List<ParentModelClass> parentModelClassesList;
    Context context;
    OnParentClickListener onParentClickListener;
    public ParentAdapter(List<ParentModelClass> parentModelClassesList, Context context, OnParentClickListener onParentClickListener) {
        this.parentModelClassesList = parentModelClassesList;
        this.context = context;
        this.onParentClickListener = onParentClickListener;
    }

    @NonNull
    @Override
    public ParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_parent,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_parent_title.setText(parentModelClassesList.get(position).title);
        ChildAdapter childAdapter;
        childAdapter = new ChildAdapter(parentModelClassesList.get(position).childModelClassesList,context);
        holder.rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_child.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
        childAdapter.setOnChildrenClickListener(childPosition -> onParentClickListener.onChildItemClick(position, childPosition));
    }
    @Override
    public int getItemCount() {
        return parentModelClassesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rv_child;
        TextView tv_parent_title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rv_child = itemView.findViewById(R.id.rv_child);
            tv_parent_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
