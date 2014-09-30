package com.tryhard.mvp.app.route;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import android.support.v4.app.Fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import com.tryhard.mvp.app.R;

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
        Context ctx = container.getContext();
        getActivity().setTitle(R.string.title_activity_bus_stop);
        gridView.setAdapter(new RouteItemAdapter(ctx, RouteDataHolder.getInstance().getBusStopList()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                RouteDataHolder dataHolder = RouteDataHolder.getInstance();
                BusStopInformation info = dataHolder.getBusStopInfo(position);

                String name = info.getName();
                Fragment newFragment = new RouteDetailFragment();
                Bundle args = new Bundle();

                dataHolder.setBusStopSelected(position);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.content_frame, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return v;
    }
}
