package com.tryhard.mvp.app.structs;

import android.util.Log;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Overlay;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.tryhard.mvp.app.map.ResourceManager;

import java.util.*;

/**
 * Created by andreq on 10/9/14.
 */
public class PathFinder {
    private static int NOT_FOUND = -1;
    private static double WALK_SPEED = 0.633;
    private static double BUS_SPEED = 6.33;
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
                long busLen = getBusLen(r.paths);
                long walkLen = getWalkLen(from, start) + getWalkLen(to, end);
                Log.d("BusLen", "" + busLen);
                Log.d("WalkLen", "" + walkLen);
                r.busTime = (long) (busLen / BUS_SPEED);
                r.walkTime = (long) (walkLen / WALK_SPEED);
                r.totalTime = r.busTime + r.walkTime;
                r.nextBus = getNextBus(sitRoute);
                r.busLabel = route.getKey();

                payback.routes.add(r);
                payback.orientation = sitRoute.orientation;
            }
        }
        Collections.sort(payback.routes);
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

    private long getWalkLen(BusStop from, BusStop to) {
        return (long)(from.distanceTo(to) / WALK_SPEED);
    }

    private Date getNextBus(SitRoute route) {
        return new Date();
    }

    private long getBusLen(List<Path> paths) {
        double ans = 0;
        for (Path p: paths) {
            List<LatLng> overlay = p.getPoints();
            double length = getLength(overlay);
            ans += length;
        }
        return (long)(ans);
    }

    private double getLength(List<LatLng> overlay) {
        double length = 0;
        for (int i = 0, len = overlay.size(); i < len - 1; i++) {
            length += overlay.get(i).distanceTo(overlay.get(i+1));
        }
        return length;
    }
}
