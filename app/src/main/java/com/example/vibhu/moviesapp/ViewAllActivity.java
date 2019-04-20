package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewAllActivity extends AppCompatActivity {
    TextView textView;
    int count;
    ArrayList<NowPlayingHeirarchy.ResultsBean> users=new ArrayList<>();
    ArrayList<PopularHeirarchy.ResultsBean> users2=new ArrayList<>();
    ArrayList<TopRatedHeirarchy.ResultsBean> users3=new ArrayList<>();
    UsesRecyclerAdapter usesRecyclerAdapter;
    UsesRecyclerAdapter2 usesRecyclerAdapter2;
    UsesRecyclerAdapter3 usesRecyclerAdapter3;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        textView=findViewById(R.id.viewtext);
        recyclerView=findViewById(R.id.recyclerview);
        usesRecyclerAdapter=new UsesRecyclerAdapter(users, this, new UsesRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        usesRecyclerAdapter2=new UsesRecyclerAdapter2(users2, this, new UsesRecyclerAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {


            }
        });
        usesRecyclerAdapter3=new UsesRecyclerAdapter3(users3, this, new UsesRecyclerAdapter3.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        count=1;
        Intent intent=getIntent();
        int check1=intent.getIntExtra("nowplaying",-1);
        int check2=intent.getIntExtra("popular",-1);
        int check3=intent.getIntExtra("toprated",-1);
        String name=intent.getStringExtra("newstring");
        textView.setText(name);
        if(check1!=-1)
        {
            recyclerView.setAdapter(usesRecyclerAdapter);
            showAll1();
        }
        else if(check2!=-1)
        {
            recyclerView.setAdapter(usesRecyclerAdapter2);
            showAll2();
        }
        else if(check3!=-1)
        {
            recyclerView.setAdapter(usesRecyclerAdapter3);
            showAll3();
        }
    }
    private void showAll3() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TopRatedHeirarchy> call=customApi.getTopRated(1+"");
        call.enqueue(new Callback<TopRatedHeirarchy>() {
            @Override
            public void onResponse(Call<TopRatedHeirarchy> call, Response<TopRatedHeirarchy> response) {
                TopRatedHeirarchy newObj=response.body();
                if(response!=null)
                {
                    users3.addAll(newObj.getResults());
                }
                usesRecyclerAdapter3.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TopRatedHeirarchy> call, Throwable t) {

                Toast.makeText(ViewAllActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showAll2() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<PopularHeirarchy> call=customApi.getPopular(1+"");
        call.enqueue(new Callback<PopularHeirarchy>() {
            @Override
            public void onResponse(Call<PopularHeirarchy> call, Response<PopularHeirarchy> response) {
                PopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    users2.addAll(newObj.getResults());
                }
                usesRecyclerAdapter2.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<PopularHeirarchy> call, Throwable t) {
                Toast.makeText(ViewAllActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showAll1() {


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<NowPlayingHeirarchy> call=customApi.getFollowersResponse(count+"");
        call.enqueue(new Callback<NowPlayingHeirarchy>() {
            @Override
            public void onResponse(Call<NowPlayingHeirarchy> call, Response<NowPlayingHeirarchy> response) {
                NowPlayingHeirarchy newObj=response.body();
                if(response!=null)
                {
                    users.addAll(newObj.getResults());
                }
                usesRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NowPlayingHeirarchy> call, Throwable t) {

                Toast.makeText(ViewAllActivity.this, "TRY AGain", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void AddToCount(View view)
    {
        String name=textView.getText().toString();
        count++;
        if(name.equals("Now Showing"))
            showAll1();

        else if(name.equals("Popular"))
            showAll2();

        else if(name.equals("Top Rated"))
            showAll3();
    }


}
