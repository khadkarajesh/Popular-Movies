package com.example.popularmovies.activities.rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rajesh on 9/19/15.
 */
public class Movie implements Parcelable {
    @SerializedName("id")
    public int id;

    @SerializedName("overview")
    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("popularity")
    public float popularity;

    @SerializedName("title")
    public String title;

    @SerializedName("vote_average")
    public float voteAverage;

    @SerializedName("vote_count")
    public int voteCount;


    public Movie(int id, String overview, String releaseDate, String posterPath, long popularity, String title, long voteAverage, int voteCount) {

        this.id = id;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(posterPath);
        dest.writeFloat(popularity);
        dest.writeString(title);
        dest.writeFloat(voteAverage);
        dest.writeInt(voteCount);
    }

    public Movie(Parcel parcel) {
        this.id = parcel.readInt();
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
        this.posterPath = parcel.readString();
        this.popularity = parcel.readFloat();
        this.title = parcel.readString();
        this.voteAverage = parcel.readFloat();
        this.voteCount = parcel.readInt();

    }


    public static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}
