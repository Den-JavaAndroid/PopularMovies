package com.example.denx7.popularmovies;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.denx7.popularmovies.model.Result;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailMovieActivity extends AppCompatActivity {

    @BindView(R.id.movie_title)
     TextView movieName;
    @BindView(R.id.overview)
     TextView movieOverview;
    @BindView(R.id.movie_poster)
     ImageView moviePoster;

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
        Result result = (Result) getIntent().getSerializableExtra("MovieInfo");
        String posterUrl = "http://image.tmdb.org/t/p/w185" + result.getPosterPath();
        Picasso.with(this).load(posterUrl).into(moviePoster);

        movieName.setText(result.getOriginalTitle());
        movieOverview.setText(result.getOverview());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
