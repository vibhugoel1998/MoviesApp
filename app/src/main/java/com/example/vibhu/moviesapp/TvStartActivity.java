package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvStartActivity extends AppCompatActivity {
    RecyclerView popularView;
    TvPopularAdapter tvPopularAdapter;
    ArrayList<TvPopularHeirarchy.TvPopularResults> arrayList2;
    RecyclerView onAirView;
    TvPopularAdapter tvOnAirAdapter;
    ArrayList<TvPopularHeirarchy.TvPopularResults> arrayList1;
    RecyclerView TopRatedView;
    TvPopularAdapter tvTopRatedAdapter;
    ArrayList<TvPopularHeirarchy.TvPopularResults> arrayList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_start);
        onAirView=findViewById(R.id.Tvmainrecyclerview1);
        arrayList1=new ArrayList<>();
        tvOnAirAdapter=new TvPopularAdapter(arrayList1, this, new TvPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TvStartActivity.this,TvDetails.class);
                TvPopularHeirarchy.TvPopularResults newObj=arrayList1.get(position);
                String title=newObj.getName();
                intent.putExtra("title",title);
                String PosterPath=newObj.getPoster_path();
                intent.putExtra("posterpath",PosterPath);
                String description=newObj.getOverview();
                intent.putExtra("overview",description);
                intent.putExtra("release",newObj.getFirst_air_date());
                intent.putExtra("id",newObj.getId());
                intent.putExtra("voteavg",newObj.getVote_average()+"");
                startActivity(intent);

            }
        });
        onAirView.setAdapter(tvOnAirAdapter);
        onAirView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        onAirView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        popularView=findViewById(R.id.Tvmainrecyclerview2);
        arrayList2=new ArrayList<>();
        tvPopularAdapter=new TvPopularAdapter(arrayList2, this, new TvPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TvStartActivity.this,TvDetails.class);
                TvPopularHeirarchy.TvPopularResults newObj=arrayList2.get(position);
                String title=newObj.getName();
                intent.putExtra("title",title);
                String PosterPath=newObj.getPoster_path();
                intent.putExtra("posterpath",PosterPath);
                String description=newObj.getOverview();
                intent.putExtra("overview",description);
                intent.putExtra("release",newObj.getFirst_air_date());
                intent.putExtra("id",newObj.getId());
                intent.putExtra("voteavg",newObj.getVote_average()+"");
                startActivity(intent);


            }
        });
        popularView.setAdapter(tvPopularAdapter);
        popularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        popularView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        TopRatedView=findViewById(R.id.Tvmainrecyclerview3);
        arrayList3=new ArrayList<>();
        tvTopRatedAdapter=new TvPopularAdapter(arrayList3, this, new TvPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TvStartActivity.this,TvDetails.class);
                TvPopularHeirarchy.TvPopularResults newObj=arrayList3.get(position);
                String title=newObj.getName();
                intent.putExtra("title",title);
                String PosterPath=newObj.getPoster_path();
                intent.putExtra("posterpath",PosterPath);
                String description=newObj.getOverview();
                intent.putExtra("overview",description);
                intent.putExtra("release",newObj.getFirst_air_date());
                intent.putExtra("id",newObj.getId());
                intent.putExtra("voteavg",newObj.getVote_average()+"");
                startActivity(intent);

            }
        });
        TopRatedView.setAdapter(tvTopRatedAdapter);
        TopRatedView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        TopRatedView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        fetchPopular();
        fetchOnAir();
        fetchTopRated();
    }

    private void fetchTopRated() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TvPopularHeirarchy> call=customApi.getTvTopRated(1+"");
        call.enqueue(new Callback<TvPopularHeirarchy>() {
            @Override
            public void onResponse(Call<TvPopularHeirarchy> call, Response<TvPopularHeirarchy> response) {
                TvPopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    arrayList3.addAll(newObj.getResults());
                }
                tvTopRatedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPopularHeirarchy> call, Throwable t) {
                Toast.makeText(TvStartActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fetchOnAir() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TvPopularHeirarchy> call=customApi.getTvOnAir(1+"");
        call.enqueue(new Callback<TvPopularHeirarchy>() {
            @Override
            public void onResponse(Call<TvPopularHeirarchy> call, Response<TvPopularHeirarchy> response) {
                TvPopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    arrayList1.addAll(newObj.getResults());
                }
                tvOnAirAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPopularHeirarchy> call, Throwable t) {
                Toast.makeText(TvStartActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchPopular() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TvPopularHeirarchy> call=customApi.getTvPopular(1+"");
        call.enqueue(new Callback<TvPopularHeirarchy>() {
            @Override
            public void onResponse(Call<TvPopularHeirarchy> call, Response<TvPopularHeirarchy> response) {
                TvPopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    arrayList2.addAll(newObj.getResults());
                }
                tvPopularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPopularHeirarchy> call, Throwable t) {
                Toast.makeText(TvStartActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void ViewTVAll(View view)
    {
        Intent intent=new Intent(this,TvViewAllActivity.class);
        intent.putExtra("On Air",1);
        intent.putExtra("newstring","On Air");
        startActivity(intent);
    }

    public void ViewTVAll2(View view)
    {
        Intent intent=new Intent(this,TvViewAllActivity.class);
        intent.putExtra("Popular",1);
        intent.putExtra("newstring","Popular");
        startActivity(intent);
    }

    public void ViewTVAll3(View view)
    {
        Intent intent=new Intent(this,TvViewAllActivity.class);
        intent.putExtra("Top Rated",1);
        intent.putExtra("newstring","Top Rated");
        startActivity(intent);
    }
}
