package com.tryhard.mvp.app.map;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.*;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;
import com.tryhard.mvp.app.BaseActivity;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.Route;
import com.tryhard.mvp.app.structs.RoutePayback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreq on 9/26/14.
 */
public class MapActivity extends BaseActivity implements MapFragment.RouteSearchListener,
                                                         RouteListFragment.ItemClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Active la conexión a internet", Toast.LENGTH_LONG).show();
        }
        super.onCreate(savedInstanceState);
        findViewById(R.id.content_frame);
        Fragment fragment = new MapFragment();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onSearchDisplayRequest(RoutePayback routes) {
        Fragment fragment = new RouteListFragment();
        Bundle b = new Bundle();
        b.putSerializable(RouteListFragment.ROUTES_FIELD, routes);
        fragment.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    @Override
    public void onItemFullScreenClick(Route route) {
        Fragment fragment = new RouteItemFragment();
        Bundle b = new Bundle();
        b.putSerializable(RouteItemFragment.ROUTE_FIELD, route);
        fragment.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
