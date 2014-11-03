package com.tryhard.mvp.app.map;

import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.Geometry;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.util.DataLoadingUtils;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.structs.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by andreq on 9/20/14.
 */
public class ResourceManager {
    HashMap<Integer, BusStop> busStopMap = new HashMap<Integer, BusStop>();
    HashMap<Integer, Path> pathMap = new HashMap<Integer, Path>();
    Integer[] bus301ida;
    Integer[] bus301vuelta;
    Integer[] bus302ida;
    Integer[] bus302vuelta;
    Integer[] bus303ida;
    Integer[] bus303vuelta;
    Handler handler;
    PathFinder pathFinder;
    boolean loaded = false;
    static ResourceManager instance;
    static String GIST_URL =
    "https://gist.githubusercontent.com/andreqi/02440611d23020fa8bba/raw/028483c58dc4bc8d431a220d57c2b7f90591c797/sit";

    private ResourceManager() {
        HandlerThread thread = new HandlerThread("ResourceManager");
        thread.start();
        handler = new Handler(thread.getLooper());
    }

    static ResourceManager getInstance() {
        synchronized (ResourceManager.class) {
            if (instance == null) {
                instance = new ResourceManager();
            }
        }
        return instance;
    }

    private String [] tokenize(String s, char delim) {
        StringTokenizer tokenizer = new StringTokenizer(s.trim().replace(delim, ' '));
        String [] tokens = new String[tokenizer.countTokens()];
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokenizer.nextToken().trim();
            System.out.print(' ' + tokens[i]);
        }
        return tokens;
    }

    private Integer[] tokenize(String s) {
        StringTokenizer tokenizer = new StringTokenizer(s.trim().replace(',', ' '));
        Integer [] tokens = new Integer[tokenizer.countTokens()];
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = Integer.parseInt(tokenizer.nextToken().trim());
        }
        return tokens;
    }

    public interface ResultListener<T> {
        void callback(boolean error, T resource);
    }

    public void getBusStops(final ResultListener<Collection<BusStop>> callback) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                load();
                callback.callback(false, busStopMap.values());
            }
        };
        handler.post(run);
    }

    public void loadRoutesBusStops() {
        bus301ida = tokenize("1,2,3,4,5,6,7,8,9,11,13,15,16,18,19,20,21,22,23,24,25,27,28,29,30,31,32,33,34,35,36,37,"+
                             "39,40,41,42,43,44,45,46,47,48,49");
        bus301vuelta = tokenize("92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,113,115,"+
                                "116,117,118,119,120,121,123,124,126,128,130,131,132,133,134,135,136,137");
        bus302ida = tokenize("1,2,3,4,5,6,7,8,9,11,13,15,16,18,19,20,21,22,23,24,25,27,28,29,30,31,32,33,34,35,36,37,38");
        bus302vuelta = tokenize("103,104,105,106,107,108,109,110,111,112,113,115,116,117,118,119,120,121,123,124,126,"+
                                "128,130,131,132,133,134,135,136,137");
        bus303ida = tokenize("8,10,12,14,17,19,22,24,26,28,29,32,36,38");
        bus303vuelta = tokenize("104,108,111,112,114,116,118,120,121,122,124,125,127,129,131");

        pathFinder = new PathFinder();
        pathFinder.addRoute("301 Ida", toSitRoute(bus301ida, "M"));
        pathFinder.addRoute("301 Vuelta", toSitRoute(bus301vuelta, "R"));

        pathFinder.addRoute("302 Ida", toSitRoute(bus302ida, "M"));
        pathFinder.addRoute("302 Vuelta", toSitRoute(bus302vuelta, "R"));

        pathFinder.addRoute("303 Ida", toSitRoute(bus303ida, "M"));
        pathFinder.addRoute("303 Vuelta", toSitRoute(bus303vuelta, "R"));
        pathFinder.setPaths(pathMap);
    }

    private SitRoute toSitRoute(Integer[] route, String orientation) {
        SitRoute sitRoute = new SitRoute();
        sitRoute.orientation = orientation;
        ArrayList<Path> paths = new ArrayList<Path>();
        ArrayList<BusStop> busStops = new ArrayList<BusStop>();
        for (int i = 0, len = route.length; i < len; i++) {
            Integer id = route[i];
            busStops.add(busStopMap.get(id));
            if (!busStopMap.containsKey(id))
                Log.d("Path", "not found bus stop" + id);
        }
        sitRoute.busStops = busStops;
        sitRoute.paths = paths;
        return sitRoute;
    }

    public void load() {
        if (loaded) return;
        loaded = true;
        try {
            FeatureCollection features = DataLoadingUtils.loadGeoJSONFromUrl(GIST_URL);
            for (Feature f : features.getFeatures()) {
                System.out.println(f.toJSON());
                Geometry g = f.getGeometry();
                JSONArray coords = (JSONArray) g.toJSON().get("coordinates");
                JSONObject props = f.getProperties();
                String description = props.getString("description");
                if (g.getType().compareTo("LineString") == 0) {
                    Coordinates[] path = new Coordinates[coords.length()];
                    for (int i = 0; i < coords.length(); i++) {
                        path[i] = new Coordinates((JSONArray) coords.get(i));
                    }
                    if (description.isEmpty()) {
                        continue;
                    }
                    String[] tokens = tokenize(description, ',');
                    String[] nextTokens = tokenize(tokens[1], '|');
                    Path p = new Path(Integer.parseInt(tokens[0]), Integer.parseInt(nextTokens[0]), path);
                    pathMap.put(p.fromId, p);
                } else if (g.getType().compareTo("Point") == 0) {
                    Coordinates c = new Coordinates(coords);
                    String[] tokens = tokenize(description, '|');
                    String title = props.getString("title");
                    BusStop busStop = new BusStop(Integer.parseInt(tokens[0]), c, title);
                    busStopMap.put(busStop.id, busStop);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        loadRoutesBusStops();
    }



    public void getPath(final BusStop from, final BusStop to, final ResultListener<RoutePayback> listener) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
            load();
            listener.callback(false, pathFinder.getPaths(from, to));
            }
        };
        handler.post(run);
    }

    public List<BusStop> getBusStopMatches(String value) {
        load();
        String lower = value.toLowerCase();
        List<BusStop> ans = new ArrayList<BusStop>();
        for (BusStop bs : busStopMap.values()) {
            if (bs.title.toLowerCase().indexOf(lower) != -1) {
                ans.add(bs);
            }
        }
        return ans;
    }
}

