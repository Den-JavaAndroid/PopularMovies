package com.example.denx7.popularmovies.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import java.util.List;

@Dao
public interface MovieInfoDao {
    @Query("SELECT * FROM " + MovieInfo.TABLE_NAME)
    Cursor getAllMovies();

    @Query("SELECT * FROM  " + MovieInfo.TABLE_NAME + " WHERE id = :movieId")
    Cursor getMovieById(long movieId);

    @Insert
    void insertMovieInfo(MovieInfo movieInfo);

    @Update
    void updateMovieInfo(MovieInfo movieInfo);

    @Query("DELETE FROM " + MovieInfo.TABLE_NAME + " WHERE id = :movieId")
    int deleteMovieInfo(long movieId);
}
