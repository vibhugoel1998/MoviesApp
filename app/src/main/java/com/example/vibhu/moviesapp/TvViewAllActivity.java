package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvViewAllActivity extends AppCompatActivity {
    TextView textView;
    int count;
    RecyclerView recyclerView;
    ArrayList<TvPopularHeirarchy.TvPopularResults> popular;
    ArrayList<TvPopularHeirarchy.TvPopularResults> onAir;
    ArrayList<TvPopularHeirarchy.TvPopularResults> TopRated;
    TvPopularAdapter popularAdapter;
    TvPopularAdapter onAirAdapter;
    TvPopularAdapter TopRatedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_view_all);
        textView=findViewById(R.id.viewtext);
        recyclerView=findViewById(R.id.recyclerview);
        popular=new ArrayList<>();
        onAir=new ArrayList<>();
        TopRated=new ArrayList<>();
        popularAdapter=new TvPopularAdapter(popular, this, new TvPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TvViewAllActivity.this,TvDetails.class);
                TvPopularHeirarchy.TvPopularResults newObj=popular.get(position);
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
        onAirAdapter=new TvPopularAdapter(onAir, this, new TvPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TvViewAllActivity.this,TvDetails.class);
                TvPopularHeirarchy.TvPopularResults newObj=onAir.get(position);
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
        TopRatedAdapter=new TvPopularAdapter(TopRated, this, new TvPopularAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(TvViewAllActivity.this,TvDetails.class);
                TvPopularHeirarchy.TvPopularResults newObj=TopRated.get(position);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        count=1;
        Intent intent=getIntent();
        int check1=intent.getIntExtra("On Air",-1);
        int check2=intent.getIntExtra("Popular",-1);
        int check3=intent.getIntExtra("Top Rated",-1);
        String name=intent.getStringExtra("newstring");
        textView.setText(name);
        if(check1!=-1)
        {
            recyclerView.setAdapter(onAirAdapter);
            showAll1();
        }
        else if(check2!=-1)
        {
            recyclerView.setAdapter(popularAdapter);
            showAll2();
        }
        else if(check3!=-1)
        {
            recyclerView.setAdapter(TopRatedAdapter);
            showAll3();
        }
    }

    private void showAll3() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TvPopularHeirarchy> call=customApi.getTvTopRated(count+"");
        call.enqueue(new Callback<TvPopularHeirarchy>() {
            @Override
            public void onResponse(Call<TvPopularHeirarchy> call, Response<TvPopularHeirarchy> response) {
                TvPopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    TopRated.addAll(newObj.getResults());
                }
                TopRatedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPopularHeirarchy> call, Throwable t) {
                Toast.makeText(TvViewAllActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showAll2() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TvPopularHeirarchy> call=customApi.getTvPopular(count+"");
        call.enqueue(new Callback<TvPopularHeirarchy>() {
            @Override
            public void onResponse(Call<TvPopularHeirarchy> call, Response<TvPopularHeirarchy> response) {
                TvPopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    popular.addAll(newObj.getResults());
                }
                popularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPopularHeirarchy> call, Throwable t) {
                Toast.makeText(TvViewAllActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void showAll1() {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<TvPopularHeirarchy> call=customApi.getTvOnAir(count+"");
        call.enqueue(new Callback<TvPopularHeirarchy>() {
            @Override
            public void onResponse(Call<TvPopularHeirarchy> call, Response<TvPopularHeirarchy> response) {
                TvPopularHeirarchy newObj=response.body();
                if(response!=null)
                {
                    onAir.addAll(newObj.getResults());
                }
                onAirAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvPopularHeirarchy> call, Throwable t) {
                Toast.makeText(TvViewAllActivity.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void AddToCount(View view)
    {
        String name=textView.getText().toString();
        count++;
        if(name.equals("On Air"))
            showAll1();

        else if(name.equals("Popular"))
            showAll2();

        else if(name.equals("Top Rated"))
            showAll3();
    }
}
