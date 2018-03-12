package com.example.denx7.popularmovies.retrofit;

import com.example.denx7.popularmovies.model.PopularMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by denx7 on 11.03.2018.
 */

public interface ThemoviedbApi {

    @GET("/3/movie/popular")
    Call<PopularMovies> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int countPage);

    @GET("/3/movie/top_rated")
    Call<PopularMovies> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int countPage);
}
