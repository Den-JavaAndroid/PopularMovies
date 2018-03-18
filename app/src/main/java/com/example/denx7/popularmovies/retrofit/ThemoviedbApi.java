package com.example.denx7.popularmovies.retrofit;

import com.example.denx7.popularmovies.model.movieinfo.PopularMovies;
import com.example.denx7.popularmovies.model.reviews.Reviews;
import com.example.denx7.popularmovies.model.videos.Videos;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



interface ThemoviedbApi {

    @GET("/3/movie/popular")
    Call<PopularMovies> getPopularMovies(@Query("api_key") String apiKey, @Query("page") int countPage);

    @GET("/3/movie/top_rated")
    Call<PopularMovies> getTopRatedMovies(@Query("api_key") String apiKey, @Query("page") int countPage);

    @GET("3/movie/{movieId}/videos ")
    Call<Videos> getVideos(@Path("movieId") int movieId, @Query("api_key") String apiKey);

    @GET("3/movie/{movieId}/reviews ")
    Call<Reviews> getReviews(@Path("movieId") int movieId, @Query("api_key") String apiKey);
}
