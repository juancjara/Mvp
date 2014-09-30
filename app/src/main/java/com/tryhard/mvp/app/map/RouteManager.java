package com.tryhard.mvp.app.map;

import android.graphics.Point;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.ItemizedIconOverlay;
import com.mapbox.mapboxsdk.overlay.ItemizedOverlay;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.structs.BusStop;
import com.tryhard.mvp.app.structs.Coordinates;
import com.tryhard.mvp.app.structs.Path;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andreq on 9/26/14.
 */
public class RouteManager {
    private MapDrawer drawer;
    private HashMap<Marker, BusStop> markerToBusStop;
    private BusStopTapListener busStopTapListener;
    RouteResult prev;

    public RouteManager(MapView mapView) {
        drawer = new MapDrawer(mapView);
        this.markerToBusStop = new HashMap<Marker, BusStop>();
    }

    public void drawBusStops(Collection<BusStop> busStops) {
        List<Marker> busStopMarkers = new ArrayList<Marker>();
        for (BusStop busStop: busStops) {
            Coordinates coord = busStop.coord;
            LatLng latLng = new LatLng(coord.latitude, coord.longitude);
            Marker marker = new Marker(busStop.title, "", latLng);
            drawer.drawMarker(marker);
            markerToBusStop.put(marker, busStop);
            busStopMarkers.add(marker);
        }
        ItemizedIconOverlay iconOverlay = new ItemizedIconOverlay(
                drawer.getMapView().getContext(),
                busStopMarkers,
                new ItemizedIconOverlay.OnItemGestureListener<Marker>() {
                    @Override
                    public boolean onItemSingleTapUp(int i, Marker marker) {
                        BusStop busStop = markerToBusStop.get(marker);
                        BusStopTapListener listener = getBusStopTapListener();
                        if (listener != null) {
                            return listener.onSingleTap(busStop);
                        }
                        return false;
                    }

                    @Override
                    public boolean onItemLongPress(int i, Marker marker) {
                        return false;
                    }
                });
        drawer.drawOverlay(iconOverlay);
        drawer.refresh();
    }

    public BusStopTapListener getBusStopTapListener() {
        return busStopTapListener;
    }

    public void setBusStopTapListener(BusStopTapListener listener) {
        busStopTapListener = listener;
    }

    public void drawResult(RouteResult value) {
        clearPrev();
        for (Path path: value.pathList) {
            drawer.drawOverlay(path.getPathOverlay());
        }
        drawer.refresh();
        prev = value;
    }

    private void clearPrev() {

    }

    public void centerMap() {
        drawer.setCenter(new LatLng(-12.075, -77.067));
        drawer.setZoom(13);
    }

    interface BusStopTapListener {
        boolean onSingleTap(BusStop busStop);
    }
}
