package com.tryhard.mvp.app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.GridView;

/**
 * Created by juancarlos on 23/09/14.
 */
public class BusStopFragment extends Fragment{
    private GridView gridView;

    public BusStopFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_list_fragment, container, false);
        gridView = (GridView) v.findViewById(R.id.route_grid_view);

        String[] data = {"301", "303", "304", "305"};
        Context ctx = container.getContext();
        gridView.setAdapter(new RouteItemAdapter(ctx, data));
        return v;
    }
}
