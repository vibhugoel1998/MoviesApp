package com.example.vibhu.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvDetails extends AppCompatActivity {
    ImageView imageView;
    TextView textView;
    TextView desc;
    TextView releaseview;
    TextView ratings;
    RecyclerView castView;
    int castid;
    RecyclerView ProductionView;
    RecyclerView similarMovies;
    castAdapter castadapter;
    ArrayList<CastHeirarchy.CastBean> arrayList;
    RelatedAdapter relatedAdapter;
    ArrayList<Related.RelatedBean> relatedArrayList;
    ProductionAdapter productionAdapter;
    ArrayList<ProductionHeirarchy.productionBean> productionArrayList;
    RecyclerView videosView;
    VideoAdapter videoAdapter;
    ArrayList<VideoHeirarchy.VideoBean> videos;
    ArrayList<Integer> cast_id_array;
    String title;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imageView=findViewById(R.id.NowPlayingImageDetails);
        textView=findViewById(R.id.NowPlayingTitleDetails);
        releaseview=findViewById(R.id.releasedate);
        castView=findViewById(R.id.castRecyclerView);
        ratings=findViewById(R.id.ratings);
        ProductionView=findViewById(R.id.ProductionRecyclerView);
        similarMovies=findViewById(R.id.SimilarRecyclerView);
        cast_id_array=new ArrayList<>();
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        String backdrop=intent.getStringExtra("posterpath");
        String release=intent.getStringExtra("release");
        int id=intent.getIntExtra("id",-1);
        ratings.setText(intent.getStringExtra("voteavg")+"/10");
        releaseview.setText("Release Date:"+release);
        textView.setText(title);
        Picasso.get().load("http:image.tmdb.org/t/p/original"+backdrop).into(imageView);
        desc=findViewById(R.id.description);
        desc.setText(intent.getStringExtra("overview"));
        setToolbarText(title);
        button=findViewById(R.id.button);
        arrayList=new ArrayList<>();
        castadapter=new castAdapter(arrayList, this, new castAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent1=new Intent(TvDetails.this,CastInfo.class);
                intent1.putExtra("castid1",cast_id_array.get(position));
                startActivity(intent1);

            }
        });
        castView.setAdapter(castadapter);
        castView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        castView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        relatedArrayList=new ArrayList<>();
        relatedAdapter=new RelatedAdapter(relatedArrayList, this, new RelatedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Related.RelatedBean newObj=relatedArrayList.get(position);
                Intent intent2=new Intent(TvDetails.this,NowPlayingMovieDetails.class);
                intent2.putExtra("title",newObj.getTitle());
                intent2.putExtra("posterpath",newObj.getPoster_path());
                intent2.putExtra("release",newObj.getRelease_date());
                intent2.putExtra("id",newObj.getId());
                intent2.putExtra("voteavg",newObj.getVote_average());
                intent2.putExtra("overview",newObj.getOverview());
                startActivity(intent2);
            }
        });
        similarMovies.setAdapter(relatedAdapter);
        similarMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        similarMovies.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        productionArrayList=new ArrayList<>();
        productionAdapter=new ProductionAdapter(productionArrayList,this);
        ProductionView.setAdapter(productionAdapter);
        ProductionView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        ProductionView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));
        videosView=findViewById(R.id.TrailerRecyclerView1);
        videos=new ArrayList<>();
        videoAdapter=new VideoAdapter(videos, this, new VideoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent1=new Intent(TvDetails.this,VideoPlaying.class);
                intent1.putExtra("keyofvideo",videos.get(position).getKey());
                startActivity(intent1);
            }
        });
        videosView.setAdapter(videoAdapter);
        videosView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        videosView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));

        SharedPreferences sharedPreferences=getSharedPreferences("fav",MODE_PRIVATE);
        String check=sharedPreferences.getString(title,"");
        if(check.equals("true"))
        {
            button.setText("LIKED");
            button.setBackgroundResource(R.drawable.buttonchanger);
        }
        else
        {
            button.setText("LIKE");
            button.setBackgroundResource(R.drawable.buttongetback);
        }


        fetchCast(id);
        fetchRelated(id);
        fetchProduction(id);
        fetchVideos(id);
    }
    private void fetchVideos(int id) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<VideoHeirarchy> call=customApi.getVideosTv(id);
        call.enqueue(new Callback<VideoHeirarchy>() {
            @Override
            public void onResponse(Call<VideoHeirarchy> call, Response<VideoHeirarchy> response) {
                VideoHeirarchy newObj=response.body();
                if(response!=null)
                {
                    videos.addAll(newObj.getResults());
                }
                videoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<VideoHeirarchy> call, Throwable t) {
                Toast.makeText(TvDetails.this, "Try Again", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void fetchProduction(int id) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<ProductionHeirarchy> call=customApi.getTVProduction(id);
        call.enqueue(new Callback<ProductionHeirarchy>() {
            @Override
            public void onResponse(Call<ProductionHeirarchy> call, Response<ProductionHeirarchy> response) {
                ProductionHeirarchy newObj=response.body();
                if(response!=null)
                {
                    productionArrayList.addAll(newObj.getProduction_companies());
                }
                productionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ProductionHeirarchy> call, Throwable t) {

                Toast.makeText(TvDetails.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchRelated(int id) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<Related> call=customApi.getTVRelated(id);
        call.enqueue(new Callback<Related>() {
            @Override
            public void onResponse(Call<Related> call, Response<Related> response) {
                Related newObj=response.body();
                if(response!=null)
                {
                    relatedArrayList.addAll(newObj.getResults());
                }
                relatedAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Related> call, Throwable t) {
                Toast.makeText(TvDetails.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchCast(int id) {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CustomApi customApi=retrofit.create(CustomApi.class);
        Call<CastHeirarchy> call=customApi.getTVCast(id);
        call.enqueue(new Callback<CastHeirarchy>() {
            @Override
            public void onResponse(Call<CastHeirarchy> call, Response<CastHeirarchy> response) {
                CastHeirarchy newObj=response.body();
                if(response!=null)
                {
                    for(int i=0;i<newObj.getCast().size();i++)
                    {
                        List<CastHeirarchy.CastBean> abc=newObj.getCast();
                        CastHeirarchy.CastBean getObj=abc.get(i);
                        castid=getObj.getId();
                        cast_id_array.add(castid);
                        arrayList.add(getObj);
                    }
                }
                castadapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<CastHeirarchy> call, Throwable t) {
                Toast.makeText(TvDetails.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setToolbarText(final String title) {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    textView.setText("");
                    collapsingToolbarLayout.setTitle(title);
                    imageView.setVisibility(View.GONE);
                    isShow = true;
                }
                else if(isShow) {
                    textView.setText(title);
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    imageView.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
    }
    public void AddToFavourites(View view)
    {
        SharedPreferences sharedPreferences=getSharedPreferences("fav",MODE_PRIVATE);
        String check=sharedPreferences.getString(title,"");
        if(check=="true")
        {
            button.setText("LIKE");
            button.setBackgroundResource(R.drawable.buttongetback);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(title,"false");
            editor.commit();
        }
        else
        {
            button.setText("LIKED");
            button.setBackgroundResource(R.drawable.buttonchanger);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(title,"true");
            editor.commit();
        }
    }

}
