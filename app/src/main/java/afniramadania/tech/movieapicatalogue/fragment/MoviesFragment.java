 package afniramadania.tech.movieapicatalogue.fragment;


import android.graphics.Movie;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.adapter.MovieAdapter;
import afniramadania.tech.movieapicatalogue.model.MovieModel;
import afniramadania.tech.movieapicatalogue.viewmodel.MoviesViewModel;

 /**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private MovieAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private MoviesViewModel movieViewModel;

    private Observer<ArrayList<MovieModel>> getMovie = new Observer<ArrayList<MovieModel>>() {
        @Override
        public void onChanged(ArrayList<MovieModel> movies) {
            if (movies != null) {
                adapter.setData(movies);
            }
            showLoading(false);
        }
    };

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new MovieAdapter();

        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        progressBar = view.findViewById(R.id.pb_movie);
        rv = view.findViewById(R.id.rv_movie);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        movieViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        movieViewModel.getMovies().observe(this, getMovie);
        movieViewModel.setMovies("EXTRA_MOVIE");

        showLoading(true);
        return view;
    }


    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}
