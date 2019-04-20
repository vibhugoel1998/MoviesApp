package com.example.vibhu.moviesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.UserViewHolder> {
    interface OnItemClickListener {

        void onItemClick(int position);
    }
    ArrayList<VideoHeirarchy.VideoBean> users;
    Context context;
    OnItemClickListener listener;

    public VideoAdapter(ArrayList<VideoHeirarchy.VideoBean> users, Context context, OnItemClickListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.videolayout,parent,false);
        UserViewHolder holder = new UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        VideoHeirarchy.VideoBean newObj=users.get(position);
        String key=newObj.getKey();
        Picasso.get().load("https://img.youtube.com/vi/"+key+"/0.jpg").into(holder.imageView);
        holder.textView.setText(newObj.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        View itemView;
        TextView textView;

        public UserViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageofvideo);
            textView=itemView.findViewById(R.id.videoname);
            this.itemView=itemView;
        }
    }

}
