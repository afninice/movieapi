package afniramadania.tech.movieapicatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import afniramadania.tech.movieapicatalogue.BuildConfig;
import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.adapter.ItemResult;

import static afniramadania.tech.movieapicatalogue.database.DatabaseContract.CONTENT_URI;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private Cursor cursor;

    public StackRemoteViewFactory(Context applicationContext, Intent intent) {
        context = applicationContext;
    }

    @Override
    public void onCreate() {
        cursor = context.getContentResolver().query(
                CONTENT_URI,
                null,
                null,
                null,
                null
        );
    }


    @Override
    public RemoteViews getViewAt(int i) {
        ItemResult item = getItem(i);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.favorite_widget);

        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context)
                    .asBitmap()
                    .load(BuildConfig.IMG_WIDGET_URL + item.getPosterPath())
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        rv.setImageViewBitmap(R.id.imageView, bitmap);

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieTvWidget.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }


    private ItemResult getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid!");
        }

        return new ItemResult(cursor);
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public int getCount() {
        return cursor.getCount();
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }
    @Override
    public boolean hasStableIds() {
        return false;
    }


}
