package com.tryhard.mvp.app.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tryhard.mvp.app.R;

import java.util.List;

/**
 * Created by andreq on 9/30/14.
 */
public class RouteListFragment extends Fragment {
    static String ROUTES_FIELD = "ROUTES";
    List<ResourceManager.Route> routes;

    public RouteListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routes = (List<ResourceManager.Route>)getArguments().get(ROUTES_FIELD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_route_list_fragment, container, false);
        return v;
    }
}
