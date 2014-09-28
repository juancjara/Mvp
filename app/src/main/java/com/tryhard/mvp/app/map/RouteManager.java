package com.tryhard.mvp.app.map;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.structs.Path;

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
        for (Path path: value.pathList) {
            drawer.drawOverlay(path.getPathOverlay());
        }
        drawer.refresh();
        prev = value;
    }

    void clearPrev() {

    }

    public void centerMap() {
        drawer.setCenter(new LatLng(-12.075, -77.067));
        drawer.setZoom(13);
    }
}
