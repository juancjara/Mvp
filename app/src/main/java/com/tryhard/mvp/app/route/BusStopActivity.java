package com.tryhard.mvp.app.route;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import com.tryhard.mvp.app.BaseActivity;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.route.BusStopFragment;


public class BusStopActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.content_frame);
        Fragment fragment = new BusStopFragment();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
            .replace(R.id.content_frame, fragment)
            .commit();
    }


}
