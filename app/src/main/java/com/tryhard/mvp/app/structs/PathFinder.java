package com.tryhard.mvp.app.structs;

import com.tryhard.mvp.app.map.ResourceManager;

import java.util.*;

/**
 * Created by andreq on 10/9/14.
 */
public class PathFinder {
    private static int NOT_FOUND = -1;
    private HashMap<String, SitRoute> routes;
    private HashMap<Integer, Path> nextPath;

    public PathFinder() {
        this.routes = new HashMap<String, SitRoute>();
    }

    public void addRoute(String key, SitRoute route) {
        routes.put(key, route);
    }

    public void setPaths(HashMap<Integer, Path> nextPath) {
        this.nextPath = nextPath;
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
        for (Map.Entry<String, SitRoute> route : routes.entrySet()) {
            SitRoute sitRoute = route.getValue();
            ArrayList<BusStop> busStops = sitRoute.busStops;
            ArrayList<Path> paths = sitRoute.paths;
            int idxFrom = findNearest(busStops, from);
            int idxTo = findNearest(busStops, to);
            if (idxFrom < idxTo) {
                BusStop start = busStops.get(idxFrom);
                BusStop end = busStops.get(idxTo);
                Route r = new Route();
                r.walkStart = from;
                r.walkEnd = to;
                r.busStart = start;
                r.busEnd = end;
                r.paths = new ArrayList<Path>();
                r.walks = new ArrayList<Path>();
                acumulatePaths(start, end, r.paths);
                acumulateWalk(from, start, r.walks);
                acumulateWalk(end, to, r.walks);
                r.busTime = getBusTime(r.paths);
                r.walkTime = getWalkTime(from, start) + getWalkTime(to, end);
                r.nextBus = getNextBus(route.getKey());
                r.busLabel = route.getKey();
                payback.routes.add(r);
                payback.orientation = sitRoute.orientation;
            }
        }

        return payback;
    }

    private void acumulateWalk(BusStop from, BusStop to, List<Path> acum) {
        if(from.id != to.id) {
            Coordinates[] coords = new Coordinates[2];
            coords[0] = from.coord;
            coords[1] = to.coord;
            Path path = new Path(-1, -1, coords);
            acum.add(path);
        }
    }

    private void acumulatePaths(BusStop from, BusStop to, List<Path> acum) {
        Integer idx = from.id;
        while (idx != to.id) {
            Path path = nextPath.get(idx);
            acum.add(path);
            idx = path.toId;
        }
    }

    private long getWalkTime(BusStop from, BusStop to) {
        return 0;
    }

    private Date getNextBus(String key) {
        return new Date();
    }

    private long getBusTime(List<Path> paths) {
        return 0;
    }
}
