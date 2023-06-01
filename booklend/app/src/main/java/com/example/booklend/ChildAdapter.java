package com.example.booklend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ViewHolder>{

    List<ChildModelClass> childModelClassesList;
    Context context;
    OnChildrenClickListener onChildrenClickListener;
    public void setOnChildrenClickListener(OnChildrenClickListener onItemClickListener) {
        this.onChildrenClickListener = onItemClickListener;
    }
    public ChildAdapter(List<ChildModelClass> childModelClassesList, Context context) {
        this.childModelClassesList = childModelClassesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ChildAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_children,null,false);
        return new ViewHolder(view,onChildrenClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChildAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(childModelClassesList.get(position).book.getImageUri()).into(holder.iv_child_image);
    }

    @Override
    public int getItemCount() {
        return childModelClassesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_child_image;
        public ViewHolder(@NonNull View itemView, OnChildrenClickListener onChildrenClickListener) {
            super(itemView);
            iv_child_image = itemView.findViewById(R.id.iv_child_item);
            itemView.setOnClickListener(view -> {
                if (onChildrenClickListener != null){
                    int childPosition = getAdapterPosition();
                    if (childPosition != RecyclerView.NO_POSITION){
                        onChildrenClickListener.OnItemClick(childPosition);
                    }
                }
            });
        }
    }
}
