package com.tryhard.mvp.app.structs;

import com.tryhard.mvp.app.map.ResourceManager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by andreq on 10/9/14.
 */
public class RoutePayback implements Serializable {
    public BusStop from;
    public BusStop to;
    public String orientation;
    public List<Route> routes;
}