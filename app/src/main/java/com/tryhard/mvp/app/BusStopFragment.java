package com.tryhard.mvp.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;

import android.support.v4.app.Fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by juancarlos on 23/09/14.
 */
public class BusStopFragment extends Fragment{
    private GridView gridView;
    String[] data = {"301", "303", "304", "305"};

    public BusStopFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.route_list_fragment, container, false);
        gridView = (GridView) v.findViewById(R.id.route_grid_view);

        Context ctx = container.getContext();
        gridView.setAdapter(new RouteItemAdapter(ctx, data));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Fragment newFragment = new RouteDetailFragment();
                Bundle args = new Bundle();
                args.putString(RouteDetailFragment.KEY_SEL, "" + position);
                args.putString(RouteDetailFragment.TEXT, data[position]);
                newFragment.setArguments(args);
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
