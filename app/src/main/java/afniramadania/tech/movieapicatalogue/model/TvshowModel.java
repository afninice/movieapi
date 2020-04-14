package afniramadania.tech.movieapicatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TvshowModel implements Parcelable {

    public static final Creator<TvshowModel> CREATOR = new Creator<TvshowModel>() {
        @Override
        public TvshowModel createFromParcel(Parcel in) {
            return new TvshowModel(in);
        }

        @Override
        public TvshowModel[] newArray(int size) {
            return new TvshowModel[size];
        }
    };
    private String title, overview, poster;
    private int id;

    public TvshowModel() {

    }

    protected TvshowModel(Parcel in) {
        title = in.readString();
        overview = in.readString();
        poster = in.readString();
        id = in.readInt();
    }

    public TvshowModel(JSONObject object) {
        try {
            String title = object.getString("name");
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
