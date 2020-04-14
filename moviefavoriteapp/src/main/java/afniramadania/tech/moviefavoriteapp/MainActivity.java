package afniramadania.tech.moviefavoriteapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import static afniramadania.tech.moviefavoriteapp.DatabaseContract.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int LOAD_NOTES_ID = 110;
    private MoviesFavAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        movieAdapter = new MoviesFavAdapter(this, null, true);
        listView.setAdapter(movieAdapter);
        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }
}
