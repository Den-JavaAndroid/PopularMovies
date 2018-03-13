package com.example.denx7.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private final ArrayList<String> moviePosterPaths;
    private ItemClickListener mClickListener;
    private final Context context;

    // data is passed into the constructor
    MoviesAdapter(Context context, ArrayList<String> moviePosterPaths) {
        this.mInflater = LayoutInflater.from(context);
        this.moviePosterPaths = moviePosterPaths;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        String posterUrl = "http://image.tmdb.org/t/p/w185" + moviePosterPaths.get(i);
        Picasso.with(context).load(posterUrl).placeholder(R.drawable.movies_placeholder).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return moviePosterPaths.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());


        }

    }


    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
