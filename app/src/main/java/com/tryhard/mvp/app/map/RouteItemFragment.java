package com.tryhard.mvp.app.map;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.Route;

import java.util.List;

/**
 * Created by andreq on 10/3/14.
 */
public class RouteItemFragment extends Fragment {
    public static String ROUTE_FIELD = "ROUTE";
    private Route route;
    public RouteItemFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        route = (Route)getArguments().get(ROUTE_FIELD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapbox_map, container, false);
        MapView mapView = (MapView)view.findViewById(R.id.mapbox_map_id);
        RouteManager routeManager = new RouteManager(mapView);
        routeManager.drawRoute(route);
        return view;
    }
}
