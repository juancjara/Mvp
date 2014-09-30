package com.tryhard.mvp.app.map;

import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.tryhard.mvp.app.BaseActivity;
import com.tryhard.mvp.app.R;

/**
 * Created by andreq on 9/26/14.
 */
public class MapActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.content_frame);
        Fragment fragment = new MapFragment();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
}
