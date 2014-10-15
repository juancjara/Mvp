package com.tryhard.mvp.app.structs;

import com.mapbox.mapboxsdk.geometry.LatLng;

/**
 * Created by andreq on 9/20/14.
 */
public class BusStop {
    public int id;
    public Coordinates coord;
    public LatLng latLng;
    public String title;

    public BusStop(int id, Coordinates coord, String title) {
        this.coord = coord;
        this.id = id;
        this.title = title;
        this.latLng = new LatLng(coord.latitude, coord.longitude);
    }

    @Override
    public String toString() {
        return title;
    }

    public double distanceTo(BusStop curBus) {
        return latLng.distanceTo(curBus.latLng);
    }
}
