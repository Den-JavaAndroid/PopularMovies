package com.example.denx7.popularmovies.provider;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.denx7.popularmovies.data.MovieContract;
import com.example.denx7.popularmovies.data.MovieInfo;
import com.example.denx7.popularmovies.data.MovieInfoDbHelper;

public class MovieInfoContentProvider extends ContentProvider {


    private MovieInfoDbHelper movieInfoDbHelper;
    public static final String AUTHORITY = "com.example.dexn7.popularmovies.provider";
    public static final Uri URI_MOVIE = Uri.parse(
            "content://" + AUTHORITY + "/" + MovieInfo.TABLE_NAME);

    /**
     * The match code for some items in the MovieInfo table.
     */
    private static final int CODE_MOVIE_DIR = 1;

    /**
     * The match code for an item in the MovieInfo table.
     */
    private static final int CODE_MOVIE_ITEM = 2;


    private static final UriMatcher sUriMatcher = buildUriMatcher();


    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, MovieInfo.TABLE_NAME, CODE_MOVIE_DIR);
        matcher.addURI(AUTHORITY, MovieInfo.TABLE_NAME + "/*", CODE_MOVIE_ITEM);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        movieInfoDbHelper = new MovieInfoDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE_DIR: {
                cursor = movieInfoDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_MOVIE_ITEM: {
                String movieId = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{movieId};
                cursor = movieInfoDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry.MOVIE_ID + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase database = movieInfoDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){
            case CODE_MOVIE_DIR:
                long id = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if(id >0) returnUri = ContentUris.withAppendedId(URI_MOVIE, id);
                else throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase database = movieInfoDbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case CODE_MOVIE_DIR:
                throw new IllegalArgumentException("Invalid URI, cannot update without ID" + uri);
            case CODE_MOVIE_ITEM:
                final Context context = getContext();
                if (context == null) {
                    return 0;
                }
                String movieId = uri.getLastPathSegment();
                int deletedCout = database.delete(MovieContract.MovieEntry.TABLE_NAME,
                        MovieContract.MovieEntry.MOVIE_ID + " = ? ", new String[]{movieId});
                return deletedCout;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
