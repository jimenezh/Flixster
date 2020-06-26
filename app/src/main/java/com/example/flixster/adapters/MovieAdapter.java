package com.example.flixster.adapters;

import android.app.usage.ConfigurationStats;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;

import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflate layout and return inside view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("MovieAdapter", "onCreateViewHolder");
//        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false );
        ItemMovieBinding binding = ItemMovieBinding.inflate(LayoutInflater.from(context));
        return new ViewHolder(binding);
    }

    // Populate data and bind it to view holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("MovieAdapter", "onBindViewHolder "+position);
        // Get movie at position
        Movie movie = movies.get(position);
        // Bind data of movie into holder
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ItemMovieBinding itemMovieBinding;

        public ViewHolder(@NonNull ItemMovieBinding itemMovieBinding) {
            super(itemMovieBinding.getRoot());
            this.itemMovieBinding = itemMovieBinding;
            // Adding listener
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            itemMovieBinding.tvTitle.setText(movie.getTitle());
            itemMovieBinding.tvOverview.setText(movie.getOverview());
            // Setting image with glide
            String imageUrl;
            int placeholder;
            int margin = 10;
            int radius = 30;
            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath();
                placeholder = R.drawable.backdrop_placeholder;
            } else{
                imageUrl = movie.getPosterPath();
                placeholder = R.drawable.movie_placeholder;
            }
            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(placeholder).into(itemMovieBinding.ivPoster);
        }

        @Override
        public void onClick(View view) {
            // position of holder
            int position = getAdapterPosition();
            // Checks if position is valid
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }




}
