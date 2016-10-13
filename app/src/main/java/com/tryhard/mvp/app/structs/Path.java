package com.tryhard.mvp.app.structs;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.PathOverlay;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andreq on 9/20/14.
 */
public class Path implements Serializable{
    public int fromId;
    public int toId;
    public Coordinates[] coords;

    public Path(int fromId, int toId, Coordinates[] coords) {
        this.coords = coords;
        this.fromId = fromId;
        this.toId = toId;

    }

    public List<LatLng> getPoints() {
        List<LatLng> points = new ArrayList<LatLng>();
        for (Coordinates c : coords) {
            points.add(new LatLng(c.latitude, c.longitude));
        }
        return points;
    }

    public PathOverlay getPathOverlay() {
        PathOverlay overlay = new PathOverlay();
        overlay.addPoints(getPoints());
        return overlay;
    }
}
