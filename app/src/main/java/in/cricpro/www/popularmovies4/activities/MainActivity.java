package in.cricpro.www.popularmovies4.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.cricpro.www.popularmovies4.R;
import in.cricpro.www.popularmovies4.adapters.MovieRecyclerViewAdapter;
import in.cricpro.www.popularmovies4.model.Movie;

public class MainActivity extends AppCompatActivity {

    //TODO: (always) remove the api key in the below link before uploading to Github
    private static final String POPULAR_MOVIES_JSON_URL = "https://api.themoviedb.org/3/discover/movie?api_key=***************************&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    //JSON Part
    private int page;
    private static final String JSON_KEY_PAGE = "page";
    private static final String JSON_KEY_RESULTS = "results";

    //JSON Movie key
    private static final String JSON_KEY_VOTE_COUNT = "vote_count";
    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_VIDEO = "video";
    private static final String JSON_KEY_VOTE_AVERAGE = "vote_average";
    private static final String JSON_KEY_TITLE = "title";
    private static final String JSON_KEY_POPULARITY = "popularity";
    private static final String JSON_KEY_POSTER_PATH = "poster_path";
    private static final String JSON_KEY_ORIGINAL_LANGUAGE = "original_language";
    private static final String JSON_KEY_ORIGINAL_TITLE = "original_title";
    private static final String JSON_KEY_BACK_DROP = "backdrop_path";
    private static final String JSON_KEY_ADULT = "adult";
    private static final String JSON_KEY_OVERVIEW = "overview";
    private static final String JSON_KEY_RELEASE_DATE = "release_date";

    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Movie> movieList;
    private RecyclerView moviesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        moviesRecyclerView = findViewById(R.id.movie_recyclerview_id);
        jsonRequest();
    }

    private void jsonRequest() {

        request = new JsonArrayRequest(POPULAR_MOVIES_JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++){

                    try{
                        jsonObject = response.getJSONObject(i);
                        Movie movie = new Movie();
                        movie.setVote_count(jsonObject.getInt(JSON_KEY_VOTE_COUNT));
                        movie.setId(jsonObject.getInt(JSON_KEY_ID));
                        movie.setVideo(jsonObject.getBoolean(JSON_KEY_VIDEO));
                        movie.setVote_average(jsonObject.getDouble(JSON_KEY_VOTE_AVERAGE));
                        movie.setPopularity(jsonObject.getDouble(JSON_KEY_POPULARITY));
                        movie.setPoster_path(jsonObject.getString(JSON_KEY_POSTER_PATH));
                        movie.setTitle(jsonObject.getString(JSON_KEY_TITLE));
                        movie.setOriginal_language(jsonObject.getString(JSON_KEY_ORIGINAL_LANGUAGE));
                        movie.setOriginal_title(jsonObject.getString(JSON_KEY_ORIGINAL_TITLE));
                        movie.setBackdrop_path(jsonObject.getString(JSON_KEY_BACK_DROP));
                        movie.setAdult(jsonObject.getBoolean(JSON_KEY_ADULT));
                        movie.setOverview(jsonObject.getString(JSON_KEY_OVERVIEW));
                        movie.setRelease_date(jsonObject.getString(JSON_KEY_RELEASE_DATE));
                        movieList.add(movie);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    setupRecyclerView(movieList);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void setupRecyclerView(List<Movie> movieList) {
        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, movieList);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesRecyclerView.setAdapter(movieRecyclerViewAdapter);
    }
}
