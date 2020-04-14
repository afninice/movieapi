package afniramadania.tech.movieapicatalogue.fragment;


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
import afniramadania.tech.movieapicatalogue.adapter.TvshowAdapter;
import afniramadania.tech.movieapicatalogue.model.TvshowModel;
import afniramadania.tech.movieapicatalogue.viewmodel.TvshowViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowFragment extends Fragment {

    private TvshowAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private TvshowViewModel tvViewModel;

    private Observer<ArrayList<TvshowModel>> getTv = new Observer<ArrayList<TvshowModel>>() {
        @Override
        public void onChanged(ArrayList<TvshowModel> tv) {
            if (tv != null) {
                adapter.setData(tv);
            }
            showLoading(false);
        }
    };


    public TvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new TvshowAdapter();

        View view = inflater.inflate(R.layout.fragment_tvshow, container, false);

        progressBar = view.findViewById(R.id.pb_tv);
        rv = view.findViewById(R.id.rv_tv);
        rv.setLayoutManager(new LinearLayoutManager(this.getContext()));
        rv.setAdapter(adapter);

        tvViewModel = ViewModelProviders.of(this).get(TvshowViewModel.class);
        tvViewModel.getTv().observe(this, getTv);
        tvViewModel.setTv("EXTRA_TV");

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
