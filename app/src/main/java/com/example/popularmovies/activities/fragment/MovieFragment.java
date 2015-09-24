package com.example.popularmovies.activities.fragment;


import android.app.Fragment;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.R;
import com.example.popularmovies.activities.adapter.MovieAdapter;
import com.example.popularmovies.activities.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {

    int page;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    GridLayoutManager gridLayoutManager;

    private static final int NO_OF_COLUMN = 2;

    private ArrayList<Movie> movieArrayList;

    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        //recyclerView= (RecyclerView) view.findViewById(R.id.recyclerView);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new MovieAsyncTask().execute(1);
    }

    private void setRecyclerView() {
        gridLayoutManager = new GridLayoutManager(getActivity(), NO_OF_COLUMN);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(10, 10, 10, 10);
            }
        });
        recyclerView.setAdapter(new MovieAdapter(movieArrayList));
    }

    public class MovieAsyncTask extends AsyncTask<Integer, Void, ArrayList<Movie>> {


        final String RESULTS = "results";
        final String MOVIE_ID = "id";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String POPULARITY = "popularity";
        final String TITLE = "title";
        final String VOTE_AVERAGE = "vote_average";
        final String VOTE_COUNT = "vote_count";

        public ArrayList<Movie> getMovieDataFromJson(String result) throws JSONException {

            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);
            ArrayList<Movie> movieArrayList = new ArrayList<>();
            Movie movie = null;

            for (int i = 0; i < jsonArray.length(); i++) {

                int movieId = jsonArray.getJSONObject(i).getInt(MOVIE_ID);
                String overView = jsonArray.getJSONObject(i).getString(OVERVIEW);
                String releaseDate = jsonArray.getJSONObject(i).getString(RELEASE_DATE);
                String posterPath = jsonArray.getJSONObject(i).getString(POSTER_PATH);
                long popularity = jsonArray.getJSONObject(i).getLong(POPULARITY);
                String title = jsonArray.getJSONObject(i).getString(TITLE);
                long voteAverage = jsonArray.getJSONObject(i).getLong(VOTE_AVERAGE);
                int voteCount = jsonArray.getJSONObject(i).getInt(VOTE_COUNT);

                movie = new Movie(movieId, overView, releaseDate, posterPath, popularity, title, voteAverage, voteCount);
                movieArrayList.add(movie);
            }
            return movieArrayList;

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Integer... params) {

            HttpURLConnection urlConnection;
            BufferedReader reader = null;

            String result = null;

            final String BASE_URL = "http://api.themoviedb.org/3/movie";
            final String API_KEY = "api_key";
            final String PAGE = "page";

            String moviesCategories = "popular";
            String apiKey = "3d9f6ef05faa3072ee2caf7fb6870964";
            String pageNumber = "2";


            Uri builtUri = Uri.parse(BASE_URL)
                    .buildUpon()
                    .appendPath(moviesCategories)
                    .appendQueryParameter(PAGE, pageNumber)
                    .appendQueryParameter(API_KEY, apiKey).build();

            try {
                URL url = new URL(builtUri.toString());
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();


                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();

                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                        // But it does make debugging a *lot* easier if you print out the completed
                        // buffer for debugging.
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        return null;
                    }
                    result = buffer.toString();


                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            try {
                return getMovieDataFromJson(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            movieArrayList = movies;
            setRecyclerView();
        }
    }


}
