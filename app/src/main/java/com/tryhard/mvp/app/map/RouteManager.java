package com.tryhard.mvp.app.map;

import android.graphics.Color;
import android.graphics.Paint;
import android.content.Context;
import android.graphics.Point;
import com.mapbox.mapboxsdk.geometry.BoundingBox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.*;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.R;
import com.tryhard.mvp.app.structs.BusStop;
import com.tryhard.mvp.app.structs.Coordinates;
import com.tryhard.mvp.app.structs.Path;
import com.tryhard.mvp.app.structs.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andreq on 9/26/14.
 */
public class RouteManager {
    private static double PADDING = 0.005;
    private MapDrawer drawer;
    private HashMap<Marker, BusStop> markerToBusStop;
    private BusStopTapListener busStopTapListener;
    private Icon busIcon;
    RouteResult prev;
    Context ctx;


    public RouteManager(MapView mapView) {
        drawer = new MapDrawer(mapView);
        ctx = mapView.getContext();
        this.markerToBusStop = new HashMap<Marker, BusStop>();
        busIcon = null;
    }

    public void drawBusStops(Collection<BusStop> busStops) {
        List<Marker> busStopMarkers = new ArrayList<Marker>();
        for (BusStop busStop: busStops) {
            Marker marker = getBusStopMarker(busStop);
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

    private Marker getBusStopMarker(BusStop busStop) {
        Coordinates coord = busStop.coord;
        LatLng latLng = new LatLng(coord.latitude, coord.longitude);
        Marker marker = new Marker(busStop.title, "", latLng);
        busIcon = new Icon(ctx, Icon.Size.SMALL, "bus", "6c6c6c");
        marker.setIcon(busIcon);
        return marker;
    }

    public void drawBusStop(BusStop busStop) {
        Marker marker = getBusStopMarker(busStop);
        drawer.drawMarker(marker);
    }

    public void drawWalkPath(Path walk) {
        PathOverlay overlay = walk.getPathOverlay();
        Paint paint = overlay.getPaint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(12);
        drawer.drawOverlay(overlay);
    }

    public void drawBusPath(Path bus) {
        PathOverlay overlay = bus.getPathOverlay();
        Paint paint = overlay.getPaint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(12);
        drawer.drawOverlay(overlay);
    }

    public void centerMap() {
        drawer.setCenter(new LatLng(-12.075, -77.045));
        drawer.setZoom(13);
    }

    private BoundingBox getBoundingBox(Route route) {
       LatLng from = route.from.getLatLng();
       LatLng to = route.to.getLatLng();
       BoundingBox box = new BoundingBox(
            Math.max(from.getLatitude(), to.getLatitude()) + PADDING,
            Math.max(from.getLongitude(), to.getLongitude()),
            Math.min(from.getLatitude(), to.getLatitude()),
            Math.min(from.getLongitude(), to.getLongitude())
       );
       return box;
    }

    public void drawRoute(Route route) {
        drawBusStop(route.from);
        drawBusStop(route.to);
        for (Path busPath: route.paths) {
            drawBusPath(busPath);
        }
        for (Path walkPath: route.walks) {
            drawWalkPath(walkPath);
        }
        drawer.setZoomBBox(getBoundingBox(route));
        drawer.refresh();
    }

    interface BusStopTapListener {
        boolean onSingleTap(BusStop busStop);
    }
}
