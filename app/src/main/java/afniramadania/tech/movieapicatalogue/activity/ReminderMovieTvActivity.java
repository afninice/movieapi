package afniramadania.tech.movieapicatalogue.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import afniramadania.tech.movieapicatalogue.R;
import afniramadania.tech.movieapicatalogue.reminder.ReminderDailyReceiver;
import afniramadania.tech.movieapicatalogue.reminder.ReminderReleaseReceiver;

public class ReminderMovieTvActivity extends AppCompatActivity {

    public static String DAILY;
    public static String RELEASE;
    private static String SETTING_PREFS = "";
    private Switch dailySwitch;
    private Switch releaseSwitch;
    private boolean dailyCheck = false;
    private boolean releaseCheck = false;
    private ReminderReleaseReceiver releaseReminderReceiver;
    private ReminderDailyReceiver dailyReminderReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remindeer_movie_tv);
        setTitle("Pengaturan Notifikasi");

        releaseReminderReceiver = new ReminderReleaseReceiver();
        dailyReminderReceiver = new ReminderDailyReceiver();

        dailySwitch = findViewById(R.id.sw_daily);
        releaseSwitch = findViewById(R.id.sw_release);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        loadPref();
        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean dailyIsChecked) {
                dailyCheck = dailyIsChecked;
                setPref();
                if (dailyIsChecked) {
                    dailyReminderReceiver.dailyReminderOn(ReminderMovieTvActivity.this);
                } else {
                    dailyReminderReceiver.dailyReminderOff(ReminderMovieTvActivity.this);
                }
            }
        });
        releaseSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean releaseIsChecked) {
                releaseCheck = releaseIsChecked;
                setPref();
                if (releaseIsChecked) {
                    releaseReminderReceiver.releaseReminderOn(ReminderMovieTvActivity.this);
                } else {
                    releaseReminderReceiver.releaseReminderOff(ReminderMovieTvActivity.this);
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(DAILY, dailyCheck);
        editor.putBoolean(RELEASE, releaseCheck);
        editor.apply();
    }

    private void loadPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SETTING_PREFS, Context.MODE_PRIVATE);
        dailySwitch.setChecked(sharedPreferences.getBoolean(DAILY, false));
        releaseSwitch.setChecked(sharedPreferences.getBoolean(RELEASE, false));
    }


}