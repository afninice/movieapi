package afniramadania.tech.moviefavoriteapp;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY = "afniramadania.tech.movieapicatalogue";
    public static String TABLE_MOVIE = "MOVIE";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_MOVIE)
            .build();
    static String TABLE_TV = "TV";

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static final class MovieColoumn implements BaseColumns {
        public static String TITLE = "title";
        public static String DESCRIPTION = "overview";
        public static String PHOTO = "IMAGE";
        public static String RELEASE = "release_date";
    }

    static final class TvColoumn implements BaseColumns {
        static String TITLE = "title";
        static String DESCRIPTION = "overview";
        static String PHOTO = "IMAGE";
    }
}
