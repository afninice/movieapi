package afniramadania.tech.movieapicatalogue.viewmodel;

import android.graphics.Movie;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import afniramadania.tech.movieapicatalogue.BuildConfig;
import afniramadania.tech.movieapicatalogue.model.MovieModel;
import cz.msebera.android.httpclient.Header;

public class MoviesViewModel extends ViewModel {

    private MutableLiveData<ArrayList<MovieModel>> listMovies = new MutableLiveData<>();

    public LiveData<ArrayList<MovieModel>> getMovies() {
        return listMovies;
    }

    public void setMovies(final String movies) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieModel> listItems = new ArrayList<>();

        String language = "en-US";

        if (Locale.getDefault().getLanguage() == "Indonesia") {
            language = "id";
        }

        String url = BuildConfig.BASE_MOVIE_URL + BuildConfig.API_KEY + "&language=" + language;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        MovieModel movieItems = new MovieModel(weather);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }


}
