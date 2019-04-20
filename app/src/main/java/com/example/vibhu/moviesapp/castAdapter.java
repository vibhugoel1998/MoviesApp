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

public class castAdapter extends RecyclerView.Adapter<castAdapter.UserViewHolder> {
    interface OnItemClickListener {

        void onItemClick(int position);
    }

    ArrayList<CastHeirarchy.CastBean> users;
    Context context;
    OnItemClickListener listener;


    public castAdapter(ArrayList<CastHeirarchy.CastBean> users, Context context, OnItemClickListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.castlayout,parent,false);
        UserViewHolder holder = new UserViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        CastHeirarchy.CastBean newObj=users.get(position);
        String name=newObj.getName();
        String profile_path=newObj.getProfile_path();
        holder.textView.setText(name);
        Picasso.get().load("http://image.tmdb.org/t/p/original"+profile_path).into(holder.imageView);
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

    class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        View itemView;
        public UserViewHolder(View itemView) {
            super(itemView);
            this.itemView=itemView;
            imageView=itemView.findViewById(R.id.castimage);
            textView=itemView.findViewById(R.id.castname);
        }

    }

}
