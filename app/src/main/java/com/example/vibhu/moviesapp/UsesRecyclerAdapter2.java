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

public class UsesRecyclerAdapter2  extends RecyclerView.Adapter<UsesRecyclerAdapter2.UserViewHolder> {
    interface OnItemClickListener {

        void onItemClick(int position);
    }
    ArrayList<PopularHeirarchy.ResultsBean> users;
    Context context;
    OnItemClickListener listener;

    public UsesRecyclerAdapter2(ArrayList<PopularHeirarchy.ResultsBean> users, Context context, OnItemClickListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UsesRecyclerAdapter2.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.rowlayout,parent,false);
        UsesRecyclerAdapter2.UserViewHolder holder = new UsesRecyclerAdapter2.UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UsesRecyclerAdapter2.UserViewHolder holder, int position) {

        PopularHeirarchy.ResultsBean newObj=users.get(position);
        String title=newObj.getTitle();
        String poster_path=newObj.getPoster_path();
        int id=newObj.getId();
        // holder.textView1.setText(title);
        Picasso.get().load("http:image.tmdb.org/t/p/original"+poster_path).into(holder.imageView);
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
        TextView textView1;
        TextView textView2;
        View itemView;
        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            imageView=itemView.findViewById(R.id.rowimageid);
            textView1=itemView.findViewById(R.id.textView3);
            textView2=itemView.findViewById(R.id.textView2);

        }

    }
}
