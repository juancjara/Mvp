package com.tryhard.mvp.app.structs;

import com.tryhard.mvp.app.map.ResourceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andreq on 10/9/14.
 */
public class PathFinder {
    private static int NOT_FOUND = -1;
    private HashMap<String, ArrayList<Path>> routes;
    private HashMap<String, ArrayList<BusStop>> busStops;
    public PathFinder() {
        this.routes = new HashMap<String, ArrayList<Path>>();
        this.busStops = new HashMap<String, ArrayList<BusStop>>();
    }

    public void addRoute(String key, ArrayList<Path> route, ArrayList<BusStop> busStop) {
        routes.put(key, route);
        busStops.put(key, busStop);
    }

    int findNearest(ArrayList<BusStop> busStop, BusStop bus) {
        double nearestDist = -1;
        int index = NOT_FOUND;
        for (int idx = 0, len = busStop.size(); idx < len; idx++) {
            BusStop curBus = busStop.get(idx);
            int busId = idx;
            double dist = bus.distanceTo(curBus);
            if (index == NOT_FOUND || nearestDist > dist) {
                index = busId;
                nearestDist = dist;
            }
        }
        return index;
    }

    public RoutePayback getPaths(BusStop from, BusStop to) {
        RoutePayback payback = new RoutePayback();
        for (Map.Entry<String, ArrayList<BusStop>> route : busStops.entrySet()) {
            ArrayList<BusStop> pathBusStops = route.getValue();
            int idxFrom = findNearest(pathBusStops, from);
            int idxTo = findNearest(pathBusStops, to);
            if (idxFrom < idxTo) {

            }
        }
        return payback;
    }
}
