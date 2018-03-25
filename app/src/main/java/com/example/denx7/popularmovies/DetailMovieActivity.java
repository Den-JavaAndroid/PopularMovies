package com.example.denx7.popularmovies;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.denx7.popularmovies.data.MovieDatabase;
import com.example.denx7.popularmovies.data.MovieInfo;
import com.example.denx7.popularmovies.model.movieinfo.Result;
import com.example.denx7.popularmovies.model.reviews.Reviews;
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
    Result movieInfo;
    ArrayList<com.example.denx7.popularmovies.model.videos.Result> listVideos;
    ArrayList<com.example.denx7.popularmovies.model.reviews.Result> listReviews;
    ArrayList<String> listKeysOfVideo = new ArrayList<>();
    private VideosAdapter videosAdapter;
    private ReviewsAdapter reviewsAdapter;

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
    RecyclerView videosRecyclerView;

    @BindView(R.id.reviews)
    RecyclerView reviewsRecyclerView;

    @BindView(R.id.add_favorite)
    ToggleButton addFavorite;


    MovieDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = this.getSupportActionBar();
        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mDb = MovieDatabase.getMovieDatabase(DetailMovieActivity.this);

        setContentView(R.layout.activity_detail_movie);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            movieInfo = getIntent().getParcelableExtra("MovieInfo");
            String posterUrl = "http://image.tmdb.org/t/p/w500" + movieInfo.getBackdropPath();
            Picasso.with(this)
                    .load(posterUrl)
                    .placeholder(R.drawable.movies_placeholder)
                    .error(R.drawable.not_found)
                    .into(moviePoster);
            restClient = new RestClient();

            movieName.setText(movieInfo.getOriginalTitle());
            movieOverview.setText(movieInfo.getOverview());
            releaseDateTxv.setText(movieInfo.getReleaseDate());
            ratingTxv.setText(movieInfo.getVoteAverage().toString());

            if (isFavoriteMovie()) {
                addFavorite.setChecked(true);
            }


            addFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFavoriteMovie()) {
                        mDb.movieInfoDao().deleteMovieInfo(movieInfo.getId());
                    } else {
                        MovieInfo movieInfoData = new MovieInfo();
                        movieInfoData.setId(movieInfo.getId());
                        movieInfoData.setOverview(movieInfo.getOverview());
                        movieInfoData.setRating(movieInfo.getVoteAverage());
                        movieInfoData.setRelease_date(movieInfo.getReleaseDate());
                        movieInfoData.setTitle(movieInfo.getTitle());
                        movieInfoData.setPosterPath(movieInfo.getPosterPath());
                        mDb.movieInfoDao().insertMovieInfo(movieInfoData);
                    }
                }
            });


            //set videos
            videosRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            getVideoList();

            //set reviews
            reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            getReviewList();


        }
    }

    private boolean isFavoriteMovie() {
        return mDb.movieInfoDao().getMovieById(movieInfo.getId()) != null;
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
        restClient.getVideos(movieInfo.getId()).enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Videos videos = response.body();
                listVideos = (ArrayList<com.example.denx7.popularmovies.model.videos.Result>) videos.getResults();

                for (com.example.denx7.popularmovies.model.videos.Result result : listVideos) {
                    listKeysOfVideo.add(result.getKey());
                }
                videosAdapter = new VideosAdapter(DetailMovieActivity.this, listKeysOfVideo);
                videosAdapter.setClickListener(DetailMovieActivity.this);
                videosRecyclerView.setAdapter(videosAdapter);
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    /**
     * load reviews
     */
    private void getReviewList() {
        restClient.getReviews(movieInfo.getId()).enqueue(new Callback<Reviews>() {
            @Override
            public void onResponse(Call<Reviews> call, Response<Reviews> response) {
                Reviews reviews = response.body();
                listReviews = (ArrayList<com.example.denx7.popularmovies.model.reviews.Result>) reviews.getResults();
                reviewsAdapter = new ReviewsAdapter(DetailMovieActivity.this, listReviews);
                reviewsRecyclerView.setAdapter(reviewsAdapter);
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
