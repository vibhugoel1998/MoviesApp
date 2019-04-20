package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OpeningActivity extends AppCompatActivity {
    RecyclerView recyclerView1;
    UsesRecyclerAdapter usesRecyclerAdapter;
    ArrayList<NowPlayingHeirarchy.ResultsBean> users;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);
        recyclerView1=findViewById(R.id.mainrecyclerview1);
        users=new ArrayList<>();
        usesRecyclerAdapter=new UsesRecyclerAdapter(users, this, new UsesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView1.setAdapter(usesRecyclerAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView1.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        fetchNowPlaying();

    }

    private void fetchNowPlaying() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<NowPlayingHeirarchy> call=customApi.getFollowersResponse(1+"");
        call.enqueue(new Callback<NowPlayingHeirarchy>() {
            @Override
            public void onResponse(Call<NowPlayingHeirarchy> call, Response<NowPlayingHeirarchy> response) {
                NowPlayingHeirarchy newObj=response.body();
                if(response!=null)
                {
                    users.clear();
                    users.addAll(newObj.getResults());
                }
                usesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NowPlayingHeirarchy> call, Throwable t) {

                Toast.makeText(OpeningActivity.this, "TRY AGain", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
