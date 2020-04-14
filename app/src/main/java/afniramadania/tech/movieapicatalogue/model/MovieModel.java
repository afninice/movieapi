package afniramadania.tech.movieapicatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class MovieModel implements Parcelable {

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    @SerializedName(value = "title", alternate = {"name"})
    private String title;
    @SerializedName("overview")
    private String overview;
    @SerializedName("poster_path")
    private String poster;
    private int id;

    public MovieModel() {

    }

    protected MovieModel(Parcel in) {
        title = in.readString();
        overview = in.readString();
        poster = in.readString();
        id = in.readInt();
    }

    public MovieModel(JSONObject object) {
        try {
            String title = object.getString("title");
            String overview = object.getString("overview");
            String poster_path = object.getString("poster_path");
            int id = object.getInt("id");

            this.title = title;
            this.poster = poster_path;
            this.overview = overview;
            this.id = id;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(poster);
        dest.writeInt(id);
    }

}
