package com.example.denx7.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private final ArrayList<String> videoKeys;
    private ItemClickListener mClickListener;
    private final Context context;

    VideosAdapter(Context context, ArrayList<String> videoKeys) {
        this.mInflater = LayoutInflater.from(context);
        this.videoKeys = videoKeys;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        String videoImgUrl = "http://img.youtube.com/vi/" + videoKeys.get(i) + "/0.jpg";
        Picasso.with(context)
                .load(videoImgUrl)
                .placeholder(R.drawable.movies_placeholder)
                .error(R.drawable.not_found)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return videoKeys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.trailer_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, getAdapterPosition());
        }

    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
