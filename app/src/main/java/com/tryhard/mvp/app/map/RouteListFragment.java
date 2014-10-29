package com.tryhard.mvp.app.map;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.BusStop;
import com.tryhard.mvp.app.structs.Route;
import com.tryhard.mvp.app.structs.RoutePayback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by andreq on 9/30/14.
 */
public class RouteListFragment extends Fragment {
    static String ROUTES_FIELD = "ROUTES";
    private RoutePayback routes;
    private ItemClickListener itemClickListener;

    public interface ItemClickListener {
        public void onItemFullScreenClick(Route route);
    }

    public RouteListFragment() {}

    private void setText(View parent, int id, String text) {
        TextView view = (TextView) parent.findViewById(id);
        view.setText(text);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        itemClickListener = (MapActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        routes = (RoutePayback)args.get(ROUTES_FIELD);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map_route_list_fragment, container, false);
        ListView routeList = (ListView)v.findViewById(R.id.map_route_list);
        MapRouteListAdapter adapter =
                new MapRouteListAdapter(getActivity(),
                                        R.layout.map_route_list_item,
                                        routes);
        routeList.setAdapter(adapter);
        setText(v, R.id.map_route_header_from, routes.from.title);
        setText(v, R.id.map_route_header_to, routes.to.title);
        setText(v, R.id.route_orientation, routes.orientation);
        return v;
    }

    class MapRouteListAdapter extends ArrayAdapter<Route> {
        private RoutePayback payback;
        private int resource;
        private LayoutInflater inflater;
        public MapRouteListAdapter(Context context,
                                   int resource,
                                   RoutePayback routes) {
            super(context, resource);
            this.payback = routes;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.resource = resource;
        }

        @Override
        public int getCount() {
            return payback.routes.size();
        }

        @Override
        public Route getItem(int position) {
            return payback.routes.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final Route route = getItem(position);
            View routeView = convertView;
            if (convertView == null) {
                routeView = inflater.inflate(this.resource, parent, false);
            }
            setText(routeView, R.id.map_item_bus_label, route.busLabel);
            setText(routeView, R.id.map_item_from_label, route.walkStart.title);
            setText(routeView, R.id.map_item_to_label, route.walkEnd.title);
            setText(routeView, R.id.next_bus_item_label, new SimpleDateFormat("HH:mm a").format(route.nextBus));
            setText(routeView, R.id.walk_time_item_label, route.getBusTimeStr());
            setText(routeView, R.id.bus_time_item_label, route.getBusTimeStr());

            MapView mapView = (MapView)routeView.findViewById(R.id.map_mapview_item);
            ImageButton goFullScreen = (ImageButton)routeView.findViewById(R.id.map_item_fullscreen);

            RouteManager routeManager = new RouteManager(mapView);
            routeManager.drawRoute(route);
            goFullScreen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemFullScreenClick(route);
                }
            });
            return routeView;
        }
    }
}
