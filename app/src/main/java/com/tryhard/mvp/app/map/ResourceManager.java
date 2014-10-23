package com.tryhard.mvp.app.map;

import android.os.Handler;
import android.os.HandlerThread;
import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.Geometry;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.util.DataLoadingUtils;
import com.mapbox.mapboxsdk.views.MapView;
import com.tryhard.mvp.app.structs.BusStop;
import com.tryhard.mvp.app.structs.Coordinates;
import com.tryhard.mvp.app.structs.Path;
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
    boolean loaded = false;
    static ResourceManager instance;
    static String GIST_URL =
     "https://gist.githubusercontent.com/andreqi/02440611d23020fa8bba/raw/3f2451d45fdd4e547ca2e865f6c664e0c31f5f4b/sit";
    private ResourceManager() {
        loadRoutesBusStops();
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
        bus301ida = tokenize("1,2,3,4,5,6,7,8,9,11,13,15,16,18,19,20,21,22,23,24,25,27,28,29,30,31,32,33,34,35,36,37,39,40,41,42");
        bus301vuelta = tokenize("100,101,102,103,104,105,106,107,108,109,110,111,112,113,115,116,117,118,119,120,121,123,124,126,128,130,131,132,133,134,135,136,137");
        bus302ida = tokenize("1,2,3,4,5,6,7,8,9,11,13,15,16,18,19,20,21,22,23,24,25,27,28,29,30,31,32,33,34,35,36,37,39");
        bus302vuelta = tokenize("103,104,105,106,107,108,109,110,111,112,113,115,116,117,118,119,120,121,123,124,126,128,130,131,132,133,134,135,136,137");
        bus303ida = tokenize("8,10,12,14,17,19,22,24,26,28,29,32,36,38");
        bus303vuelta = tokenize("104,108,111,112,114,116,118,120,121,122,124,125,127,129,131");
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
                    String[] tokens = tokenize(description, ',');
                    Path p = new Path(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), path);
                    pathMap.put(p.fromId, p);
                } else if (g.getType().compareTo("Point") == 0) {
                    Coordinates c = new Coordinates(coords);
                    String[] tokens = tokenize(description, '|');
                    String title = props.getString("title").toLowerCase();
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
    }

    public class Route implements Serializable {
        public int routeId;
        public List<Path> paths;
    }

    public void getPath(final int from, final int to, final ResultListener<List<Route>> listener) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                load();
                // mock algorithm
                List<Route> route = new ArrayList<Route>();
                for (int i = 0; i < 3; i++) {
                    Route res = new Route();
                    res.routeId = 301 + i;
                    res.paths = new ArrayList<Path>();
                    for (int j = 0; j < 3; j++) {
                        res.paths.add(pathMap.get(j + i * 3));
                    }
                    route.add(res);
                }
                listener.callback(false, route);
            }
        };
        handler.post(run);
    }

    public List<BusStop> getBusStopMatches(String value) {
        load();
        List<BusStop> ans = new ArrayList<BusStop>();
        for (BusStop bs : busStopMap.values()) {
            if (bs.title.indexOf(value) != -1) {
                ans.add(bs);
            }
        }
        return ans;
    }
}

