package com.tryhard.mvp.app.map;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.HashMap;

/**
 * Created by andreq on 9/26/14.
 */
public class RouteManager {
    private MapDrawer drawer;
    RouteResult prev;
    public RouteManager(MapView mapView) {
        drawer = new MapDrawer(mapView);
    }

    void drawResult(RouteResult value) {
        clearPrev();
        prev = value;
    }

    void clearPrev() {

    }

    public void centerMap() {
        drawer.setCenter(new LatLng(-12.075, -77.067));
        drawer.setZoom(13);
    }
}
