package com.tryhard.mvp.app.structs;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreq on 9/20/14.
 */
public class Path {
    public int fromId;
    public int toId;
    public Coordinates[] coords;
    PathOverlay overlay;
    private List<LatLng> points;

    public Path(int fromId, int toId, Coordinates[] coords) {
        this.coords = coords;
        this.fromId = fromId;
        this.toId = toId;
        points = new ArrayList<LatLng>();
        for (Coordinates c : coords) {
            points.add(new LatLng(c.latitude, c.longitude));
        }
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public PathOverlay getPathOverlay() {
        if (overlay == null) {
            overlay = new PathOverlay();
            overlay.addPoints(points);
        }
        return overlay;
    }
}
