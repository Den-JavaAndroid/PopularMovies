package com.example.denx7.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.denx7.popularmovies.model.PopularMovies;
import com.example.denx7.popularmovies.model.Result;
import com.example.denx7.popularmovies.retrofit.RestClient;
import com.example.denx7.popularmovies.settings.SettingsActivity;
import com.example.denx7.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private MoviesAdapter adapter;
    private RestClient restClient;
    @BindView(R.id.movies)
    RecyclerView recyclerView;
    private ArrayList<Result> listMovies;
    @BindView(R.id.progress_load_movies)
    ProgressBar progressBarLoadMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restClient = new RestClient();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridAutofitLayoutManager(this, 400));

        if (savedInstanceState != null) {
            listMovies = savedInstanceState.getParcelableArrayList("MovieInfo");
            setMovies(listMovies);
        } else if (NetworkUtils.isOnline(this))
            loadMoviesSortedByPreference(PreferenceManager.getDefaultSharedPreferences(this));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MovieInfo", listMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void loadMoviesSortedByPreference(SharedPreferences sharedPreferences) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        String sortBy = sharedPreferences.getString(getString(R.string.sort_by_key), getString(R.string.popular));
        if (sortBy.equals(getString(R.string.popular))) getPopularMovieList();
        if (sortBy.equals(getString(R.string.rating))) getTopRatedMovieList();
    }


    /**
     * load popular movies
     */
    private void getPopularMovieList() {
        progressBarLoadMovies.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        restClient.getPopularMoviesList(1).enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                PopularMovies popularMovies = response.body();
                listMovies = (ArrayList<Result>) popularMovies.getResults();
                setMovies(listMovies);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    /**
     * load top rated movies
     */
    private void getTopRatedMovieList() {
        progressBarLoadMovies.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        restClient.getTopRatedMoviesList(1).enqueue(new Callback<PopularMovies>() {
            @Override
            public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                PopularMovies popularMovies = response.body();
                listMovies = (ArrayList<Result>) popularMovies.getResults();
                setMovies(listMovies);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void setMovies(ArrayList<Result> listMovies) {

        ArrayList<String> topRatedMoviesPosterPath = new ArrayList<>();
        for (Result result : listMovies)
            topRatedMoviesPosterPath.add(result.getPosterPath());

        adapter = new MoviesAdapter(MainActivity.this, topRatedMoviesPosterPath);
        adapter.setClickListener(MainActivity.this);
        progressBarLoadMovies.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra("MovieInfo", listMovies.get(position));
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sort_by_key))) {
            loadMoviesSortedByPreference(sharedPreferences);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
