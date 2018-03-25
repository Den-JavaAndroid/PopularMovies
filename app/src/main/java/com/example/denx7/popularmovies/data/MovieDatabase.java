package com.example.denx7.popularmovies.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {MovieInfo.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase{
    private static MovieDatabase INSTANCE;
    public abstract MovieInfoDao movieInfoDao();

    public static MovieDatabase getMovieDatabase(Context contecxt){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(contecxt, MovieDatabase.class, "c")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
