package afniramadania.tech.moviefavoriteapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static afniramadania.tech.moviefavoriteapp.DatabaseContract.MovieColoumn.DESCRIPTION;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.MovieColoumn.PHOTO;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.MovieColoumn.TITLE;
import static afniramadania.tech.moviefavoriteapp.DatabaseContract.getColumnString;

public class MoviesFavAdapter extends CursorAdapter {


    public MoviesFavAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return view;
    }

    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            TextView textViewTitle;
            TextView textViewOverview;
            ImageView imgPoster;
            textViewTitle = view.findViewById(R.id.txt_title);
            imgPoster = view.findViewById(R.id.img_photo);
            textViewOverview = view.findViewById(R.id.txt_description);

            textViewTitle.setText(getColumnString(cursor, TITLE));
            textViewOverview.setText(getColumnString(cursor, DESCRIPTION));
            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w185" + getColumnString(cursor, PHOTO))
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPoster);
        }

    }

}
