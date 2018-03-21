package com.example.denx7.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.denx7.popularmovies.model.reviews.Result;

import java.util.ArrayList;


public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private final LayoutInflater mInflater;
    private final ArrayList<Result> reviews;
    private ItemClickListener mClickListener;
    private final Context context;

    ReviewsAdapter(Context context, ArrayList<Result> reviews) {
        this.mInflater = LayoutInflater.from(context);
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        holder.reviewTxv.setText(reviews.get(i).getContent());
        holder.reviewAutorTxv.setText(reviews.get(i).getAuthor());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView reviewTxv;
        final TextView reviewAutorTxv;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewTxv = itemView.findViewById(R.id.review);
            reviewAutorTxv = itemView.findViewById(R.id.review_autor);
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
