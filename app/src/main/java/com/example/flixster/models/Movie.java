package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import org.parceler.Parcel;

@Parcel
public class Movie {

    String backdropPath;
    String posterPath;
    String title;
    String overview;
    double voteAverage;
    double popularity;

    public Movie() {
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath) ;
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    // Parses JSON for individual movie into a Movie object
    public Movie(JSONObject jsonObject) throws JSONException {
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        popularity = jsonObject.getDouble("popularity");
    }

    // Parses array of JSONs into a list of Movie objects. Uses Movie method to do so
    public static List<Movie> fromJSONArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies  = new ArrayList<>();
        for(int i =0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return movies;
    }
}
