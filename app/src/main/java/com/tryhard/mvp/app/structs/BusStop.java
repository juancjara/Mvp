package com.tryhard.mvp.app.structs;

/**
 * Created by andreq on 9/20/14.
 */
public class BusStop {
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
}
