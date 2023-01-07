package com.example.findzy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.MyViewHolder> {

    private final List<PostModelClass> postItems;
    private final Context context;

    public MyPostsAdapter(List<PostModelClass> postItems, Context context) {
        this.postItems = postItems;
        this.context = context;
    }


    @NonNull
    @Override
    public MyPostsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.posts_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsAdapter.MyViewHolder holder, int position) {
        PostModelClass postItem = postItems.get(position);

        holder.serviceName.setText(postItem.getServiceName());
        holder.serviceCategory.setText(postItem.getCategory());
        holder.serviceLocation.setText(postItem.getLocation());
    }

    @Override
    public int getItemCount() {
        return postItems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName, serviceLocation, serviceCategory;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceName = itemView.findViewById(R.id.service_name);
            serviceCategory = itemView.findViewById(R.id.service_category);
            serviceLocation = itemView.findViewById(R.id.service_location);
        }
    }
}
