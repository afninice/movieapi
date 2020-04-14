package afniramadania.tech.movieapicatalogue.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import afniramadania.tech.movieapicatalogue.model.TvshowModel;

import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.TABLE_TV;

public class TvshowHelper {

    private static final String DATABASE_TABLE = TABLE_TV;
    private static DatabaseHellper databaseHelper;
    private static TvshowHelper INSTANCE;
    private static SQLiteDatabase database;

    public TvshowHelper(Context context) {
        databaseHelper = new DatabaseHellper(context);
        database = databaseHelper.getWritableDatabase();
    }

    public static TvshowHelper getINSTANCE(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TvshowHelper(context);

                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();
        if (database.isOpen()) {
            database.close();
        }
    }

    public ArrayList<TvshowModel> getAllTv() {
        ArrayList<TvshowModel> items = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null, null, null, null, null, DatabaseContract.TvColoumn._ID, null);
        cursor.moveToFirst();
        TvshowModel mItem;
        if (cursor.getCount() > 0) {
            do {
                mItem = new TvshowModel();
                mItem.setId(cursor.getInt(0));
                mItem.setOverview(String.valueOf(cursor.getString(2)));
                mItem.setPoster(String.valueOf(cursor.getString(3)));
                mItem.setTitle(String.valueOf(cursor.getString(1)));
                items.add(mItem);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return items;
    }

    public Boolean getOne(String name) {
        String querySingleRecord = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + DatabaseContract.TvColoumn.TITLE + " " + " LIKE " + "'" + name + "'";
        Cursor cursor = database.rawQuery(querySingleRecord, null);
        cursor.moveToFirst();
        Log.d("cursor", String.valueOf(cursor.getCount()));

        if (cursor.getCount() > 0) {
            return true;
        } else if (cursor.getCount() == 0) {
            return false;
        }
        return false;
    }

    public long insertTv(TvshowModel mItem) {
        ContentValues args = new ContentValues();
        args.put(DatabaseContract.TvColoumn.TITLE, mItem.getTitle());
        args.put(DatabaseContract.TvColoumn.PHOTO, mItem.getPoster());
        args.put(DatabaseContract.TvColoumn.DESCRIPTION, mItem.getOverview());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(String title) {
        return database.delete(TABLE_TV, DatabaseContract.TvColoumn.TITLE + " = " + "'" + title + "'", null);
    }

}
