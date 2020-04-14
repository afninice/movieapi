package afniramadania.tech.movieapicatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import afniramadania.tech.movieapicatalogue.MainActivity;
import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.adapter.FavoriteAdapter;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(getString(R.string.favorites_catalogue));

        FavoriteAdapter favoritePagerAdapter = new FavoriteAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager_fav);
        viewPager.setAdapter(favoritePagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs_fav);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_main_menu) {
            Intent mIntent = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
