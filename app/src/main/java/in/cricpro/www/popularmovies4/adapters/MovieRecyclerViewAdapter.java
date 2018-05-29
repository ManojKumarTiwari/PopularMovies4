package in.cricpro.www.popularmovies4.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import in.cricpro.www.popularmovies4.R;
import in.cricpro.www.popularmovies4.model.Movie;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieItemViewHolder> {

    private static final String BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String SIZE = "w500";

    private Context mContext;
    private List<Movie> movieList;
    private RequestOptions option;

    public MovieRecyclerViewAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.placeholder).error(R.drawable.placeholder);
    }

    @NonNull
    @Override
    public MovieItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        view = layoutInflater.inflate(R.layout.movie_item_cardview, parent, false);
        return new MovieItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder holder, int position) {

        holder.movieTitleTv.setText(movieList.get(position).getTitle());

        //Creating Url to fetch movie poster
        Movie movie = movieList.get(position);
        String imageURL = BASE_URL + SIZE + movie.getPoster_path();
        //Load image from the internet and set it to ImageView using Glide
        Glide.with(mContext).load(imageURL).apply(option).into(holder.movieThumbnailIv);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieItemViewHolder extends RecyclerView.ViewHolder{

        ImageView movieThumbnailIv;
        TextView movieTitleTv;

        public MovieItemViewHolder(View itemView) {
            super(itemView);

            movieThumbnailIv = (ImageView) itemView.findViewById(R.id.movie_thumbnail_image_view_id);
            movieTitleTv = (TextView) itemView.findViewById(R.id.movie_title_text_view_id);
        }
    }
}
