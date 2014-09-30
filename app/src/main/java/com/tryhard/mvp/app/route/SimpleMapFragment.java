package com.tryhard.mvp.app.route;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.TouchImageView;

/**
 * Created by juancarlos on 28/09/14.
 */
public class SimpleMapFragment extends Fragment {
    private TouchImageView image;
    public SimpleMapFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_simple_map_fragment, container, false);
        BusStopInformation data = RouteDataHolder.getInstance().getSelectedBusStopInfo();
        image = (TouchImageView) v.findViewById(R.id.simple_map_touch_view);
        image.setImageResource(data.getImageId());
        return v;
    }
}