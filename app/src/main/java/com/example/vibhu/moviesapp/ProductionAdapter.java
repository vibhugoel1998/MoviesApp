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

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.UserViewHolder> {

    ArrayList<ProductionHeirarchy.productionBean> arrayList;
    Context context;

    public ProductionAdapter(ArrayList<ProductionHeirarchy.productionBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
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
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        ProductionHeirarchy.productionBean newObj=arrayList.get(position);
        String logo=newObj.getLogo_path();
        String name=newObj.getName();
        holder.textView.setText(name);
        Picasso.get().load("http://image.tmdb.org/t/p/original"+logo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public UserViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.castimage);
            textView=itemView.findViewById(R.id.castname);
        }
    }
}

