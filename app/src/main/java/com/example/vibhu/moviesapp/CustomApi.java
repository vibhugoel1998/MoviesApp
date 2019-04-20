package com.example.vibhu.moviesapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CustomApi {

    @GET("3/movie/now_playing?api_key=c66dc50c8923e950de5657fe61428b4d&language=en-US")
    Call<NowPlayingHeirarchy> getFollowersResponse (@Query("page") String page);

    @GET("3/movie/popular?api_key=c66dc50c8923e950de5657fe61428b4d&language=en-US&page=1")
    Call<PopularHeirarchy> getPopular(@Query("page") String page);

    @GET("3/movie/top_rated?api_key=c66dc50c8923e950de5657fe61428b4d&language=en-US&page=1")
    Call<TopRatedHeirarchy> getTopRated(@Query("page") String page);
}
