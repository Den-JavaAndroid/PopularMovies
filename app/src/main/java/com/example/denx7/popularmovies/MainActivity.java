package com.example.denx7.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.denx7.popularmovies.model.PopularMovies;
import com.example.denx7.popularmovies.model.Result;
import com.example.denx7.popularmovies.retrofit.RestClient;
import com.example.denx7.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ItemClickListener {

    MoviesAdapter adapter;
    RestClient restClient;
    @BindView(R.id.movies)
    RecyclerView recyclerView;
    ArrayList<Result> listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        boolean netrork = NetworkUtils.isOnline(this);
        if(netrork)
        getPopularMovieList();
//        getTopRatedMovieList();
    }

    /**
     * load popular movies
     */
    public void getPopularMovieList() {
        restClient.getPopularMoviesList(1).enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                PopularMovies popularMovies = response.body();
                listMovies = (ArrayList<Result>) popularMovies.getResults();
                ArrayList<String> popularMoviesPosterPath = new ArrayList<>();
                for (Result result : listMovies)
                    popularMoviesPosterPath.add(result.getPosterPath());

                adapter = new MoviesAdapter(MainActivity.this, popularMoviesPosterPath);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    /**
     * load top rated movies
     */
    public void getTopRatedMovieList() {
        restClient.getTopRatedMoviesList(1).enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                PopularMovies popularMovies = response.body();
                listMovies = (ArrayList<Result>) popularMovies.getResults();
                ArrayList<String> topRatedMoviesPosterPath = new ArrayList<>();
                for (Result result : listMovies)
                    topRatedMoviesPosterPath.add(result.getPosterPath());

                adapter = new MoviesAdapter(MainActivity.this, topRatedMoviesPosterPath);
                adapter.setClickListener(MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra("MovieInfo", listMovies.get(position));
        startActivity(intent);
//                getTopRatedMovieList();

    }
}
