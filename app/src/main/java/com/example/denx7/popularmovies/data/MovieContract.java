package com.example.denx7.popularmovies.data;

import android.provider.BaseColumns;

public class MovieContract {

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "favoriteMovie";
        public static final String MOVIE_ID = "id";
        public static final String TITLE = "title";
        public static final String RATING = "vote_average";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String POSTER = "poster";
        public static final String BACKDROP_POSTER = "backdrop_poster";
    }
}

