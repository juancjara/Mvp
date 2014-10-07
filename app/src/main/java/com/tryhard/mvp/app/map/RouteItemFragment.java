package com.tryhard.mvp.app.map;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tryhard.mvp.app.R;

import java.util.List;

/**
 * Created by andreq on 10/3/14.
 */
public class RouteItemFragment extends Fragment {
    public static String ROUTE_FIELD = "ROUTE";
    private ResourceManager.Route route;
    public RouteItemFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        route = (ResourceManager.Route)getArguments().get(ROUTE_FIELD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapbox_map, container, false);
        return view;
    }
}
