package com.tryhard.mvp.app.map;

import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.overlay.Marker;
import com.mapbox.mapboxsdk.overlay.Overlay;
import com.mapbox.mapboxsdk.overlay.OverlayManager;
import com.mapbox.mapboxsdk.views.MapView;

import java.util.List;

/**
 * Created by andreq on 9/26/14.
 */
public class MapDrawer {
    MapView view;
    OverlayManager overlayManager;
    public MapDrawer(MapView view) {
        this.view = view;
        this.overlayManager = view.getOverlayManager();
    }

    public void drawMarker(Marker marker) {
        view.addMarker(marker);
    }

    public void removeMarker(Marker marker) {
        view.removeMarker(marker);
    }

    public void drawOverlay(Overlay path) {
        overlayManager.add(path);
    }

    public void removeOverlay(Overlay path) {
       overlayManager.remove(path);
    }

    public void refresh() {
        view.invalidate();
    }

    public void setCenter(LatLng center) {
        view.setCenter(center);
    }

    public void setZoom(float zoom) {
        view.setZoom(zoom);
    }
}