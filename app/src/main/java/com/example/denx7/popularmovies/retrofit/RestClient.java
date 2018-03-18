package com.example.denx7.popularmovies.retrofit;



import com.example.denx7.popularmovies.BuildConfig;
import com.example.denx7.popularmovies.model.movieinfo.PopularMovies;
import com.example.denx7.popularmovies.model.reviews.Reviews;
import com.example.denx7.popularmovies.model.videos.Videos;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = BuildConfig.API_KEY;
    private final ThemoviedbApi themoviedbApi;
    public static String BASE_VIDEO_URL = "https://www.youtube.com/watch?v=";

    public RestClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        themoviedbApi = retrofit.create(ThemoviedbApi.class);
    }

    public Call<PopularMovies> getPopularMoviesList(int page) {
        return themoviedbApi.getPopularMovies(API_KEY, page);
    }

    public Call<PopularMovies> getTopRatedMoviesList(int page){
        return themoviedbApi.getTopRatedMovies(API_KEY, page);
    }

    public Call<Videos> getVideos(int movieId){
        return themoviedbApi.getVideos(movieId, API_KEY);
    }

    public Call<Reviews> getReviews(int movieId){
        return themoviedbApi.getReviews(movieId, API_KEY);
    }
}
