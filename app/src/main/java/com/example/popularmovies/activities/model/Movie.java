package com.example.popularmovies.activities.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rajesh on 9/19/15.
 */

public class Movie implements Parcelable {
    public int id;
    public String overview;
    public String releaseDate;
    public String posterPath;
    public long popularity;
    public String title;
    public long voteAverage;
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
        dest.writeLong(popularity);
        dest.writeString(title);
        dest.writeLong(voteAverage);
        dest.writeInt(voteCount);
    }

    public Movie(Parcel parcel) {

        this.id = parcel.readInt();
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
        this.posterPath = parcel.readString();
        this.popularity = parcel.readLong();
        this.title = parcel.readString();
        this.voteAverage = parcel.readLong();
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
