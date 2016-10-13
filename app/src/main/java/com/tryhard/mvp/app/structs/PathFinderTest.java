package com.tryhard.mvp.app.structs;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PathFinderTest extends TestCase {

    PathFinder finder;
    SitRoute routes[] = new SitRoute[2];


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        routes[0] = new SitRoute();
        routes[0].busStops = new ArrayList<BusStop>();

        routes[1] = new SitRoute();
        routes[1].busStops = new ArrayList<BusStop>();
        for (int j = 0; j < 2; j++) {
            double x = 0;
            double y = 0;
            for (int i = 0; i < 10; i++) {
                x += 10;
                if (i % 2 == 0) {
                    y += 10;
                } else {
                    y -= 10;
                }
                BusStop busStop = new BusStop(
                    i,
                    new Coordinates(x, y),
                    "Bus" + 5
                );
                routes[j].busStops.add(busStop);
            }
        }
    }

    public void testAddRoute() throws Exception {
        finder = new PathFinder();
        finder.addRoute("301" , routes[0]);
        finder.addRoute("302" , routes[1]);
    }

    public void testFindNearestSamePoint() throws Exception {
        finder = new PathFinder();
        ArrayList<BusStop> busStops = routes[0].busStops;
        for (int i = 0; i < busStops.size(); i++) {
            int id = finder.findNearest(busStops, busStops.get(i));
            assertEquals(id, i);
        }

    }

    public void testFindNearestInFront() throws Exception {
        finder = new PathFinder();
        ArrayList<BusStop> busStops = routes[0].busStops;
        for (int i = 0; i < busStops.size(); i++) {
            BusStop x = busStops.get(i);
            BusStop front = new BusStop(
                i,
                new Coordinates(x.coord.latitude + 5, x.coord.longitude),
                "Bus"
            );
            int id = finder.findNearest(busStops, front);
            assertEquals(id, i);
        }

    }

    public void testFindNearestInPath() throws Exception {
        finder = new PathFinder();
        ArrayList<BusStop> busStops = routes[0].busStops;
        for (int i = 0; i < busStops.size(); i++) {
            BusStop x = busStops.get(i);
            BusStop near = new BusStop(
                i,
                new Coordinates(x.coord.latitude + 5, x.coord.longitude + 5),
                "Bus"
            );
            int id = finder.findNearest(busStops, near);
            assertEquals("should be the same point or the next or last one" +
                         id + " " + i, Math.abs(id - i) <= 1, true);
        }
    }

 }