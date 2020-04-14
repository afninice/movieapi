package afniramadania.tech.movieapicatalogue.database;


import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.model.MovieModel;

public interface DbCallbackMovies {

    void preExecute();

    void postExecute(ArrayList<MovieModel> items);

}
