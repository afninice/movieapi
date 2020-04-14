package afniramadania.tech.movieapicatalogue.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.BuildConfig;
import afniramadania.tech.movieapicatalogue.model.TvshowModel;
import cz.msebera.android.httpclient.Header;

public class TvshowViewModel extends ViewModel {

    private MutableLiveData<ArrayList<TvshowModel>> listTv = new MutableLiveData<>();

    public LiveData<ArrayList<TvshowModel>> getTv() {
        return listTv;
    }

    public void setTv(final String tv) {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvshowModel> listItems = new ArrayList<>();

        String url = BuildConfig.BASE_TV_URL + BuildConfig.API_KEY;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject weather = list.getJSONObject(i);
                        TvshowModel tvItems = new TvshowModel(weather);
                        listItems.add(tvItems);
                    }
                    listTv.postValue(listItems);
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
