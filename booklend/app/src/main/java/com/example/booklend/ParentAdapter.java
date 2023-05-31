package com.example.booklend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<ParentAdapter.ViewHolder>{

    List<ParentModelClass> parentModelClassesList;
    Context context;

    public ParentAdapter(List<ParentModelClass> parentModelClassesList, Context context) {
        this.parentModelClassesList = parentModelClassesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ParentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_parent,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParentAdapter.ViewHolder holder, int position) {
        holder.tv_parent_title.setText(parentModelClassesList.get(position).title);
        ChildAdapter childAdapter;
        childAdapter = new ChildAdapter(parentModelClassesList.get(position).childModelClassesList,context);
        holder.rv_child.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.rv_child.setAdapter(childAdapter);
        childAdapter.notifyDataSetChanged();
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
