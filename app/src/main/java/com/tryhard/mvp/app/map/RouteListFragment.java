package com.tryhard.mvp.app.map;

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

import java.util.List;

/**
 * Created by andreq on 9/30/14.
 */
public class RouteListFragment extends Fragment {
    static String ROUTES_FIELD = "ROUTES";
    List<ResourceManager.Route> routes;

    public RouteListFragment() {}

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
        ListView routeList = (ListView)v.findViewById(R.id.map_route_list);
        View view = inflater.inflate(R.layout.mapbox_map, container, false);
        MapView mapView = (MapView)view.findViewById(R.id.mapbox_map_id);
        mapView.setDrawingCacheEnabled(true);

        routeList.setAdapter(
                new MapRouteListAdapter(getActivity(), R.layout.map_route_list_item, routes, mapView, getResources()));
        return v;
    }

    static class MapRouteListAdapter extends ArrayAdapter<ResourceManager.Route> {
        private List<ResourceManager.Route> routes;
        private int resource;
        private LayoutInflater inflater;
        private MapView mapCacheView;
        private Resources res;
        public MapRouteListAdapter(Context context,
                                   int resource,
                                   List<ResourceManager.Route> routes,
                                   MapView mapCacheView,
                                   Resources res) {
            super(context, resource);
            this.res = res;
            this.routes = routes;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.resource = resource;
            this.mapCacheView = mapCacheView;
        }

        @Override
        public int getCount() {
            return routes.size();
        }

        @Override
        public ResourceManager.Route getItem(int position) {
            return routes.get(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ResourceManager.Route route = getItem(position);
            View routeView = convertView;
            if (convertView == null) {
                routeView = inflater.inflate(this.resource, parent, false);
            }
            TextView fromLabel = (TextView)routeView.findViewById(R.id.map_item_from_label);
            TextView toLabel = (TextView)routeView.findViewById(R.id.map_item_to_label);

            fromLabel.setText("Casa juanchi");
            toLabel.setText("Por el tim");

            TextView nextBusLabel = (TextView)routeView.findViewById(R.id.next_bus_item_label);
            TextView walkTimeLabel = (TextView)routeView.findViewById(R.id.walk_time_item_label);
            TextView busTimeLabel = (TextView)routeView.findViewById(R.id.bus_time_item_label);

            nextBusLabel.setText("5:00pm");
            walkTimeLabel.setText("10m");
            busTimeLabel.setText("2h 20m");


            /*
            ImageView mapView = (ImageView)routeView.findViewById(R.id.map_item_img_view);
            Bitmap tempBitmap = Bitmap.createBitmap(
                    mapCacheView.getMeasuredWidth(),
                    mapCacheView.getMeasuredHeight(),
                    Bitmap.Config.RGB_565);
            Canvas tempCanvas = new Canvas(tempBitmap);

            mapCacheView.draw(tempCanvas);
            mapView.setImageDrawable(new BitmapDrawable(res, tempBitmap));
            */
            return routeView;
        }
    }
}
