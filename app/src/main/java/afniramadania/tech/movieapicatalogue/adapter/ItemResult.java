package afniramadania.tech.movieapicatalogue.adapter;

import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.MovieColoumn.DESCRIPTION;
import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.MovieColoumn.PHOTO;
import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.MovieColoumn.TITLE;
import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.getColumnInt;
import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.getColumnString;
import static android.provider.BaseColumns._ID;

public class ItemResult {

    @SerializedName("overview")
    private String overview;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    public ItemResult() {
    }

    public ItemResult(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, TITLE);
        this.posterPath = getColumnString(cursor, PHOTO);
        this.overview = getColumnString(cursor, DESCRIPTION);
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "ResultItem{" +
                        "overview = '" + overview + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }

}
