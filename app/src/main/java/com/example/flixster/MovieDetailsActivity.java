package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMovieDetailsBinding;
import com.example.flixster.databinding.ActivityMovieTrailerBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity extends AppCompatActivity {

    Context context;
    Movie movie;
    ActivityMovieDetailsBinding binding;
    final String YOUTUBE_TRAILER_BASE_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=" + R.string.moviedb_key;
    final String  TAG ="MovieDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        context = getApplicationContext();

        setContentView(R.layout.activity_movie_details);
        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // unwrap the movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set the title and overview
        binding.tvTitle.setText(movie.getTitle());
        binding.tvOverview.setText(movie.getOverview());

        // vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = (float) movie.getVoteAverage();
        binding.tvRating.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        Glide.with(context).load(movie.getBackdropPath()).placeholder(R.drawable.backdrop_placeholder).into(binding.tvTrailer);

        binding.tvTrailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncHttpClient client = new AsyncHttpClient();
                String url = String.format(YOUTUBE_TRAILER_BASE_URL, movie.getId());

                client.get(url, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.d(TAG, "onSuccess");
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray result = jsonObject.getJSONArray("results");
                            Log.i(TAG, "Results: " + result.toString());
                            String youtubeId = result.getJSONObject(0).getString("key");
                            Intent intent = new Intent(context, MovieTrailerActivity.class);
                            intent.putExtra("id", youtubeId  );
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        } catch (JSONException e) {
                            Log.e(TAG, "Hit json exception");
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure");
                    }
                });


            }
        });

    }
}