package com.tryhard.mvp.app.structs;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by andreq on 9/20/14.
 */
public class Coordinates {
    public double latitude;
    public double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Coordinates (JSONArray geom) throws JSONException{
        latitude = geom.getDouble(1);
        longitude = geom.getDouble(0);
    }
}
