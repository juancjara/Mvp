package com.tryhard.mvp.app.structs;

import com.tryhard.mvp.app.map.ResourceManager;

import java.util.*;

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
        payback.from = from;
        payback.to = to;
        payback.routes = new ArrayList<Route>();
        for (Map.Entry<String, ArrayList<BusStop>> route : busStops.entrySet()) {
            ArrayList<BusStop> pathBusStops = route.getValue();
            ArrayList<Path> paths = routes.get(route.getKey());
            int idxFrom = findNearest(pathBusStops, from);
            int idxTo = findNearest(pathBusStops, to);
            if (idxFrom < idxTo) {
                BusStop start = pathBusStops.get(idxFrom);
                BusStop end = pathBusStops.get(idxTo);
                Route r = new Route();
                r.paths = new ArrayList<Path>();
                for (int idx = idxFrom; idx < idxTo; idx++) {
                    r.paths.add(paths.get(idx));
                }
                r.busTime = getBusTime(r.paths);
                r.walkTime = getWalkTime(from, start) + getWalkTime(to, end);
                r.nextBus = getNextBus(route.getKey());
                payback.routes.add(r);
            }
        }
        payback.orientation = "M";
        return payback;
    }

    private long getWalkTime(BusStop from, BusStop start) {
        return 0;
    }

    private Date getNextBus(String key) {
        return new Date();
    }

    private long getBusTime(List<Path> paths) {
        return 0;
    }
}
