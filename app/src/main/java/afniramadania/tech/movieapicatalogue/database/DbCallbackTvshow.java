package afniramadania.tech.movieapicatalogue.database;

import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.model.TvshowModel;

public interface DbCallbackTvshow {

    void preExecute();

    void postExecute(ArrayList<TvshowModel> items);



}
