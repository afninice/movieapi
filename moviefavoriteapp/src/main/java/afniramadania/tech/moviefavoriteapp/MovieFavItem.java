package afniramadania.tech.moviefavoriteapp;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static afniramadania.tech.moviefavoriteapp.DatabaseContract.MovieColoumn.DESCRIPTION;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.MovieColoumn.PHOTO;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.MovieColoumn.TITLE;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.getColumnInt;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.getColumnString;
import static android.provider.BaseColumns._ID;

public class MovieFavItem implements Parcelable {

    public static final Creator<MovieFavItem> CREATOR = new Creator<MovieFavItem>() {
        @Override
        public MovieFavItem createFromParcel(Parcel source) {
            return new MovieFavItem(source);
        }

        @Override
        public MovieFavItem[] newArray(int size) {
            return new MovieFavItem[size];
        }
    };
    private int id;
    private String titleMovie, overView, imagePoster;

    private MovieFavItem(Parcel in) {
        this.id = in.readInt();
        this.titleMovie = in.readString();
        this.overView = in.readString();
        this.imagePoster = in.readString();
    }

    public MovieFavItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.titleMovie = getColumnString(cursor, TITLE);
        this.overView = getColumnString(cursor, DESCRIPTION);
        this.imagePoster = getColumnString(cursor, PHOTO);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    private void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getOverView() {
        return overView;
    }

    private void setOverView(String overView) {
        this.overView = overView;
    }

    public String getImagePoster() {
        return imagePoster;
    }

    private void setImagePoster(String imagePoster) {
        this.imagePoster = imagePoster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titleMovie);
        dest.writeString(this.overView);
        dest.writeString(this.imagePoster);
    }
}
