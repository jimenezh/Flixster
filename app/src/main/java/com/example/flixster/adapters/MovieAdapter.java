package com.example.flixster.adapters;

import android.app.usage.ConfigurationStats;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    final String PLACEHOLDER_POSTER ="https://courses.codepath.org/course_files/android_university_fast_track/assets/flicks_movie_placeholder.gif";
    final String PLACE_HOLDER_BACKDROP ="https://courses.codepath.org/course_files/android_university_fast_track/assets/flicks_backdrop_placeholder.gif";

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Inflate layout and return inside view holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false );
        return new ViewHolder(movieView);
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

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // Adding listener
            itemView.setOnClickListener(this);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
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

            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).placeholder(placeholder).into(ivPoster);
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
