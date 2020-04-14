package afniramadania.tech.movieapicatalogue.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.BuildConfig;
import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.activity.DetailMovieActivity;
import afniramadania.tech.movieapicatalogue.model.MovieModel;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<MovieModel> mData = new ArrayList<>();
    private Context context;

    public MovieAdapter() {

    }

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<MovieModel> items) {
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgPoster;
        TextView tvJudul, tvOverview;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.txt_title);
            tvOverview = itemView.findViewById(R.id.txt_description);
            imgPoster = itemView.findViewById(R.id.img_photo);

            itemView.setOnClickListener(this);
        }

        void bind(MovieModel movie) {
            String urlImage = BuildConfig.IMG_URL + movie.getPoster();

            tvJudul.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            Glide.with(itemView.getContext())
                    .load(urlImage)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            MovieModel movie = mData.get(position);

            movie.setTitle(movie.getTitle());
            movie.setPoster(movie.getPoster());
            movie.setOverview(movie.getOverview());

            Intent moveWithObjectIntent = new Intent(itemView.getContext(), DetailMovieActivity.class);
            moveWithObjectIntent.putExtra(DetailMovieActivity.EXTRA_MOVIE, movie);
            itemView.getContext().startActivity(moveWithObjectIntent);
        }
    }
}
