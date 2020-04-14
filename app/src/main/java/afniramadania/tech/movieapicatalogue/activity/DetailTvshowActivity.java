package afniramadania.tech.movieapicatalogue.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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
import afniramadania.tech.movieapicatalogue.database.TvshowHelper;
import afniramadania.tech.movieapicatalogue.model.TvshowModel;

public class DetailTvshowActivity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

    TextView tvTitle, tvOverview;
    ImageView imgPhoto;
    FloatingActionButton fab;
    TvshowHelper tvHelper;
    Boolean act = true;
    Boolean insert = true;
    Boolean delete = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tvshow);

        setTitle(getString(R.string.tv_show_detail));

        tvTitle = findViewById(R.id.tv_title_tv);
        tvOverview = findViewById(R.id.tv_overview_tv);
        imgPhoto = findViewById(R.id.iv_photo_tv);
        fab = findViewById(R.id.btn_fav_tv);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        TvshowModel tv = getIntent().getParcelableExtra(EXTRA_TV);
        tvHelper = TvshowHelper.getINSTANCE(getApplicationContext());
        String tTitle = tv.getTitle();
        if (tvHelper.getOne(tTitle)) {
            //hapus
            act = false;
            delete = false;
            fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.blue_favorite_24dp));
        } else if (!tvHelper.getOne(tTitle)) {
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

        String url_image = BuildConfig.IMG_URL + tv.getPoster();
        tvTitle.setText(tv.getTitle());
        tvOverview.setText(tv.getOverview());
        Glide.with(DetailTvshowActivity.this)
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

    private void fabClick() {
        TvshowModel item = getIntent().getParcelableExtra(EXTRA_TV);
        if (insert && act) {
            item.setTitle(item.getTitle());
            item.setOverview(item.getOverview());
            item.setPoster(item.getPoster());
            long result = tvHelper.insertTv(item);
            if (result > 0) {
                Toast.makeText(DetailTvshowActivity.this, R.string.success, Toast.LENGTH_SHORT).show();
                fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.blue_favorite_24dp));
            } else {
                Toast.makeText(DetailTvshowActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        } else if (!delete && !act) {
            long result = tvHelper.deleteTv(item.getTitle());
            if (result > 0) {
                Intent intent = new Intent(DetailTvshowActivity.this, FavoriteActivity.class);
                startActivity(intent);
                Toast.makeText(DetailTvshowActivity.this, R.string.success_delete, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DetailTvshowActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
