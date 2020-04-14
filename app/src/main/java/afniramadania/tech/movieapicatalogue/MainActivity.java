package afniramadania.tech.movieapicatalogue;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;

import afniramadania.tech.movieapicatalogue.activity.FavoriteActivity;
import afniramadania.tech.movieapicatalogue.activity.ReminderMovieTvActivity;
import afniramadania.tech.movieapicatalogue.activity.SearchMovieActivity;
import afniramadania.tech.movieapicatalogue.activity.SearchTvshowActivity;
import afniramadania.tech.movieapicatalogue.adapter.SectionAdapter;

public class MainActivity extends AppCompatActivity {

    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionAdapter sectionsPagerAdapter = new SectionAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 0) {
                    pos = 0;
                } else if (position == 1) {
                    pos = 1;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_reminder) {
            Intent mIntent = new Intent(MainActivity.this, ReminderMovieTvActivity.class);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.action_search) {
            if (pos == 0) {
                Intent mIntent = new Intent(MainActivity.this, SearchMovieActivity.class);
                startActivity(mIntent);
            } else if (pos == 1) {
                Intent mIntent = new Intent(MainActivity.this, SearchTvshowActivity.class);
                startActivity(mIntent);
            }
        } else if (item.getItemId() == R.id.favorite_btn) {
            Intent mIntent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

}
