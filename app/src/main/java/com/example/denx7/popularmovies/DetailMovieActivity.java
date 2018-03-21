package com.example.denx7.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denx7.popularmovies.model.movieinfo.Result;
import com.example.denx7.popularmovies.model.videos.Videos;
import com.example.denx7.popularmovies.retrofit.RestClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity implements VideosAdapter.ItemClickListener {

    private RestClient restClient;
    Result result;
    ArrayList<com.example.denx7.popularmovies.model.videos.Result> listVideos;
    ArrayList<String> listKeysOfVideo = new ArrayList<>();
    private VideosAdapter adapter;

    @BindView(R.id.movie_title)
    TextView movieName;
    @BindView(R.id.overview)
    TextView movieOverview;
    @BindView(R.id.movie_poster)
    ImageView moviePoster;

    @BindView(R.id.release_date)
    TextView releaseDateTxv;

    @BindView(R.id.rating)
    TextView ratingTxv;

    @BindView(R.id.videos)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            result = getIntent().getParcelableExtra("MovieInfo");
            String posterUrl = "http://image.tmdb.org/t/p/w500" + result.getBackdropPath();
            Picasso.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.movies_placeholder)
                    .error(R.drawable.not_found)
                    .into(moviePoster);
            restClient = new RestClient();

            movieName.setText(result.getOriginalTitle());
            movieOverview.setText(result.getOverview());
            releaseDateTxv.setText(result.getReleaseDate());
            ratingTxv.setText(result.getVoteAverage().toString());

//            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            getVideoList();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * load videos
     */
    private void getVideoList() {
//        progressBarLoadMovies.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.INVISIBLE);
        restClient.getVideos(result.getId()).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Videos videos = response.body();
                listVideos = (ArrayList<com.example.denx7.popularmovies.model.videos.Result>) videos.getResults();

                for (com.example.denx7.popularmovies.model.videos.Result result : listVideos) {
                    listKeysOfVideo.add(result.getKey());
                }
                adapter = new VideosAdapter(DetailMovieActivity.this, listKeysOfVideo);
                adapter.setClickListener(DetailMovieActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(RestClient.BASE_VIDEO_URL + listKeysOfVideo.get(position))));
    }
}
