package com.example.denx7.popularmovies.data;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieInfoDao {
    @Query("SELECT * FROM movieinfo")
    List<MovieInfo> getAllMovies();

    @Query("SELECT * FROM movieinfo WHERE id = :movieId")
    MovieInfo getMovieById(int movieId);

    @Insert
    void insertMovieInfo(MovieInfo movieInfo);

    @Update
    void updateMovieInfo(MovieInfo movieInfo);

    @Query("DELETE FROM MovieInfo WHERE id = :movieId")
    void deleteMovieInfo(int movieId);
}
