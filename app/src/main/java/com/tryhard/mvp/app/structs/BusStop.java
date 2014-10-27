package com.tryhard.mvp.app.structs;

import com.mapbox.mapboxsdk.geometry.LatLng;

import java.io.Serializable;

/**
 * Created by andreq on 9/20/14.
 */
public class BusStop implements Serializable{
    public int id;
    public Coordinates coord;
    public String title;

    public BusStop(int id, Coordinates coord, String title) {
        this.coord = coord;
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }

    public LatLng getLatLng() {
        return new LatLng(coord.latitude, coord.longitude);
    }

    public double distanceTo(BusStop curBus) {
        return getLatLng().distanceTo(curBus.getLatLng());
    }
}
