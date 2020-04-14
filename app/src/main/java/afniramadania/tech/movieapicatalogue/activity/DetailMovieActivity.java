package afniramadania.tech.movieapicatalogue.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import afniramadania.tech.movieapicatalogue.BuildConfig;
import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.database.MoviesHelper;
import afniramadania.tech.movieapicatalogue.model.MovieModel;

public class DetailMovieActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    TextView tvTitle, tvOverview;
    ImageView imgPhoto;
    FloatingActionButton fab;
    MoviesHelper movieHelper;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        setTitle(getString(R.string.movie_detail));

        tvTitle = findViewById(R.id.tv_title);
        tvOverview = findViewById(R.id.tv_overview);
        imgPhoto = findViewById(R.id.img_photo);
        fab = findViewById(R.id.btn_fav_movie);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        MovieModel movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movieHelper = MoviesHelper.getINSTANCE(getApplicationContext());
        String movieTitle = movie.getTitle();
        if (movieHelper.getOne(movieTitle)) {
            //hapus
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.blue_favorite_24dp));
        } else if (!movieHelper.getOne(movieTitle)) {
            //buat
            act = true;
            insert = true;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.blue_favorite_border_24dp));
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabClick();
            }
        });

        String url_image = BuildConfig.IMG_URL + movie.getPoster();
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        Glide.with(DetailMovieActivity.this)
                .load(url_image)
                .placeholder(R.color.colorAccent)
                .dontAnimate()
                .into(imgPhoto);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void fabClick() {
        MovieModel item = getIntent().getParcelableExtra(EXTRA_MOVIE);
        if (insert && act) {
            item.setTitle(item.getTitle());
            item.setOverview(item.getOverview());
            item.setPoster(item.getPoster());
            long result = movieHelper.insertMovie(item);
            if (result > 0) {
                Toast.makeText(DetailMovieActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.blue_favorite_24dp));
            } else {
                Toast.makeText(DetailMovieActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (!delete && !act) {
            long result = movieHelper.deleteMovie(item.getTitle());
            if (result > 0) {
                Intent intent = new Intent(DetailMovieActivity.this, FavoriteActivity.class);
                startActivity(intent);
                Toast.makeText(DetailMovieActivity.this, R.string.success_delete, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailMovieActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
