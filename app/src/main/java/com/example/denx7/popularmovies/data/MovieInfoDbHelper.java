package com.example.denx7.popularmovies.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MovieInfoDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movieInfo.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + MovieContract.MovieEntry.TABLE_NAME + "( " +
            MovieContract.MovieEntry.MOVIE_ID + " integer primary key, " +
            MovieContract.MovieEntry.TITLE + " text not null, " +
            MovieContract.MovieEntry.RELEASE_DATE + " text not null, " +
            MovieContract.MovieEntry.POSTER + " text not null, " +
            MovieContract.MovieEntry.RATING + " real not null, " +
            MovieContract.MovieEntry.BACKDROP_POSTER + " text not null, " +
            MovieContract.MovieEntry.OVERVIEW + " text not null);";


    public MovieInfoDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MovieInfoDbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
