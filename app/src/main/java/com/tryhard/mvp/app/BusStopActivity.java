package com.tryhard.mvp.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class BusStopActivity extends BaseActivity {
    public static BusStopInformation[] data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data = new BusStopInformation[4];
        data[0] = new BusStopInformation("301", R.drawable.m_301);
        data[1] = new BusStopInformation("302", R.drawable.map2);
        data[2] = new BusStopInformation("303", R.drawable.map2);
        data[3] = new BusStopInformation("304", R.drawable.map2);

        findViewById(R.id.content_frame);
        Fragment fragment = new BusStopFragment();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit();
    }


}
