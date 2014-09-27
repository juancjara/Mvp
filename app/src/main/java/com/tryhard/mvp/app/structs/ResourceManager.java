package com.tryhard.mvp.app.structs;

import com.cocoahero.android.geojson.Feature;
import com.cocoahero.android.geojson.FeatureCollection;
import com.cocoahero.android.geojson.Geometry;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.PathOverlay;
import com.mapbox.mapboxsdk.util.DataLoadingUtils;
import com.mapbox.mapboxsdk.views.MapView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by andreq on 9/20/14.
 */
public class ResourceManager {
    HashMap<Integer, BusStop> busStopMap = new HashMap<Integer, BusStop>();
    HashMap<Integer, Path> pathMap = new HashMap<Integer, Path>();

    static ResourceManager instance = new ResourceManager();
    static String GIST_URL =
     "https://gist.githubusercontent.com/andreqi/02440611d23020fa8bba/raw/3f2451d45fdd4e547ca2e865f6c664e0c31f5f4b/sit";
    private ResourceManager() {}

    static ResourceManager getInstance() {
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

    public void load(final MapView mapView, final ResourceListener listener) {
        Runnable loadFeatures = new Runnable() {
            @Override
            public void run() {
                try {
                    mapView.setCenter(new LatLng(-12.075, -77.067));
                    mapView.setZoom(13);
                    FeatureCollection features = DataLoadingUtils.loadGeoJSONFromUrl(GIST_URL);
                    ArrayList<Object> uiObjects = DataLoadingUtils.createUIObjectsFromGeoJSONObjects(features, null);
                    for (Object obj : uiObjects) {
                        if (obj instanceof Marker) {
                           // mapView.addMarker((Marker) obj);
                        } else if (obj instanceof PathOverlay) {
                            //mapView.getOverlays().add((PathOverlay) obj);
                        }
                    }
                    for (Feature f : features.getFeatures()) {
                        System.out.println(f.toJSON());
                        Geometry g = f.getGeometry();
                        JSONArray coords = (JSONArray)g.toJSON().get("coordinates");
                        JSONObject props = f.getProperties();
                        String description = props.getString("description");
                        if (g.getType().compareTo("LineString") == 0) {
                            Coordinates[] path = new Coordinates[coords.length()];
                            for (int i = 0; i < coords.length(); i++) {
                                path[i] = new Coordinates((JSONArray)coords.get(i));
                            }
                            String[] tokens = tokenize(description, ',');
                            Path p = new Path(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), path);
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
                    listener.after();
                    listener.callback(true, null);
                    return;
                } catch (JSONException e) {
                    listener.after();
                    listener.callback(true, null);
                    e.printStackTrace();
                    return;
                }
                listener.callback(false, getInstance());
            }
        };
        listener.before();
        Thread t = new Thread(loadFeatures);
        t.start();
    }

    public List<PathOverlay> getPath(int from, int to) {
        List<Path> ans = new ArrayList<Path>();
        boolean found = false;
        while (pathMap.containsKey((Integer)from)) {
            Path path = pathMap.get((Integer)from);
            int next = path.toId;
            from = next;
            ans.add(path);
            if (next == to) {
                found = true;
                break;
            }
        }
        if (found) {
            List<PathOverlay> ansPath= new ArrayList<PathOverlay>();
            for (Path p: ans) {
                System.out.print(p.fromId + " ");
                PathOverlay pathOverlay = new PathOverlay();
                pathOverlay.addPoints(p.getPoints());
                ansPath.add(pathOverlay);
            }
            System.out.println();
            return ansPath;
        }
        return new ArrayList<PathOverlay>();
    }
}

