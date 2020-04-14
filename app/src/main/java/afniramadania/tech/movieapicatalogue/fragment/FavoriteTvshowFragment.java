package afniramadania.tech.movieapicatalogue.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.activity.DetailTvshowActivity;
import afniramadania.tech.movieapicatalogue.adapter.ClickItemSupport;
import afniramadania.tech.movieapicatalogue.adapter.TvshowAdapter;
import afniramadania.tech.movieapicatalogue.database.DbCallbackTvshow;
import afniramadania.tech.movieapicatalogue.database.TvshowHelper;
import afniramadania.tech.movieapicatalogue.model.TvshowModel;

import static afniramadania.tech.movieapicatalogue.activity.DetailTvshowActivity.EXTRA_TV;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteTvshowFragment extends Fragment implements DbCallbackTvshow {

    private ProgressBar mProgressBar;
    private TvshowHelper mTvHelper;
    private TvshowAdapter tvAdapter;
    private RecyclerView recyclerView;
    private ArrayList<TvshowModel> items = new ArrayList<>();


    public FavoriteTvshowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_tvshow, container, false);
        mProgressBar = view.findViewById(R.id.pb_fav_tv);
        recyclerView = view.findViewById(R.id.rv_fav_tv);

        mTvHelper = TvshowHelper.getINSTANCE(getContext());
        mTvHelper.open();

        showRecyclerView();
        recyclerView.addOnItemTouchListener(new ClickItemSupport(getContext(), recyclerView, new ClickItemSupport.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TvshowModel mItem = new TvshowModel();
                mItem.setId(items.get(position).getId());
                mItem.setPoster(items.get(position).getPoster());
                mItem.setTitle(items.get(position).getTitle());
                mItem.setOverview(items.get(position).getOverview());
                Intent detail = new Intent(getContext(), DetailTvshowActivity.class);

                detail.putExtra(EXTRA_TV, mItem);
                startActivity(detail);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));

        new LoadTvAsync(mTvHelper, this).execute();
        return view;
    }

    private void showRecyclerView() {
        tvAdapter = new TvshowAdapter(getContext());
        tvAdapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(tvAdapter);
    }


    @Override
    public void preExecute() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<TvshowModel> mItem) {
        mProgressBar.setVisibility(View.GONE);
        tvAdapter.setData(mItem);
        items.addAll(mItem);
    }

    private class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvshowModel>> {

        WeakReference<TvshowHelper> tvHelperWeakReference;
        WeakReference<DbCallbackTvshow> loadFavCallbackWeakReference;

        public LoadTvAsync(TvshowHelper tvHelper, DbCallbackTvshow context) {
            tvHelperWeakReference = new WeakReference<>(tvHelper);
            loadFavCallbackWeakReference = new WeakReference<>(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadFavCallbackWeakReference.get().preExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<TvshowModel> mItem) {
            super.onPostExecute(mItem);
            loadFavCallbackWeakReference.get().postExecute(mItem);
        }

        @Override
        protected ArrayList<TvshowModel> doInBackground(Void... voids) {
            Log.d("test", "test" + tvHelperWeakReference.get().getAllTv());
            return tvHelperWeakReference.get().getAllTv();
        }
    }

}

