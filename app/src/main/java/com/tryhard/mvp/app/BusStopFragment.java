package com.tryhard.mvp.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

/**
 * Created by juancarlos on 23/09/14.
 */
public class BusStopFragment extends Fragment{
    public BusStopFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bus_stop_fragment, container, false);
        return v;
    }
}
