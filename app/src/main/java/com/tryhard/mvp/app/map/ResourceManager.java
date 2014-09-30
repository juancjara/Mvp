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
import java.util.*;

/**
 * Created by andreq on 9/20/14.
 */
public class ResourceManager {
    HashMap<Integer, BusStop> busStopMap = new HashMap<Integer, BusStop>();
    HashMap<Integer, Path> pathMap = new HashMap<Integer, Path>();
    Handler handler;
    boolean loaded = false;
    static ResourceManager instance;
    static String GIST_URL =
     "https://gist.githubusercontent.com/andreqi/02440611d23020fa8bba/raw/3f2451d45fdd4e547ca2e865f6c664e0c31f5f4b/sit";
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

    public void getPath(final int from, final int to, final ResultListener<List<Path>> listener) {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                load();
                List<Path> ans = new ArrayList<Path>();
                boolean found = false;
                int it = from;
                while (pathMap.containsKey((Integer)it)) {
                    Path path = pathMap.get((Integer)it);
                    int next = path.toId;
                    it = next;
                    ans.add(path);
                    if (it == to) {
                        found = true;
                        break;
                    }
                }
                if (found) {
                    List<Path> ansPath= new ArrayList<Path>();
                    for (Path p: ans) {
                        ansPath.add(p);
                        System.out.println(busStopMap.get(p.fromId).title);
                    }
                    listener.callback(false, ansPath);
                    return;
                }
                listener.callback(false, new ArrayList<Path>());
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

