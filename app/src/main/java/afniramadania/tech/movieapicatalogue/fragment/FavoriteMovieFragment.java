package afniramadania.tech.movieapicatalogue.fragment;


import android.content.Intent;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.activity.DetailMovieActivity;
import afniramadania.tech.movieapicatalogue.adapter.ClickItemSupport;
import afniramadania.tech.movieapicatalogue.adapter.MovieAdapter;
import afniramadania.tech.movieapicatalogue.database.DbCallbackMovies;
import afniramadania.tech.movieapicatalogue.database.MoviesHelper;
import afniramadania.tech.movieapicatalogue.model.MovieModel;

import static afniramadania.tech.movieapicatalogue.activity.DetailMovieActivity.EXTRA_MOVIE;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteMovieFragment extends Fragment implements DbCallbackMovies {


    private ProgressBar mProgressBar;
    private MoviesHelper mMovieHelper;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private ArrayList<MovieModel> items = new ArrayList<>();

    public FavoriteMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);
        mProgressBar = view.findViewById(R.id.pb_fav_movie);
        recyclerView = view.findViewById(R.id.rv_fav_movie);

        mMovieHelper = MoviesHelper.getINSTANCE(getContext());
        mMovieHelper.open();

        showRecyclerView();
        recyclerView.addOnItemTouchListener(new ClickItemSupport(getContext(), recyclerView, new ClickItemSupport.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MovieModel mItem = new MovieModel();
                mItem.setId(items.get(position).getId());
                mItem.setPoster(items.get(position).getPoster());
                mItem.setTitle(items.get(position).getTitle());
                mItem.setOverview(items.get(position).getOverview());
                Intent detail = new Intent(getContext(), DetailMovieActivity.class);

                detail.putExtra(EXTRA_MOVIE, mItem);
                startActivity(detail);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                MovieModel mItem = new MovieModel();
                mItem.setId(items.get(position).getId());
                mItem.setPoster(items.get(position).getPoster());
                mItem.setTitle(items.get(position).getTitle());
                mItem.setOverview(items.get(position).getOverview());
                Intent detail = new Intent(getContext(), DetailMovieActivity.class);

                detail.putExtra(EXTRA_MOVIE, mItem);
                startActivity(detail);
            }
        }));

        new FavoriteMovieFragment.LoadMovieAsync(mMovieHelper, this).execute();
        return view;
    }

    private void showRecyclerView() {
        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(movieAdapter);
    }

    public void preExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void postExecute(ArrayList<MovieModel> mItem) {
        mProgressBar.setVisibility(View.GONE);
        movieAdapter.setData(mItem);
        items.addAll(mItem);
    }


    public class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<MovieModel>> {

        WeakReference<MoviesHelper> tvHelperWeakReference;
        WeakReference<DbCallbackMovies> loadFavCallbackWeakReference;

        public LoadMovieAsync(MoviesHelper movieHelper, FavoriteMovieFragment context) {
            tvHelperWeakReference = new WeakReference<>(movieHelper);
            loadFavCallbackWeakReference = new WeakReference<DbCallbackMovies>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<MovieModel> mItem) {
            super.onPostExecute(mItem);
            loadFavCallbackWeakReference.get().postExecute(mItem);
        }

        @Override
        protected ArrayList<MovieModel> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllMovie();
        }
    }
}