package com.tryhard.mvp.app.map;

import com.mapbox.mapboxsdk.geometry.BoundingBox;
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

    public MapDrawer(MapView view) {
        this.view = view;
    }

    public void disableTouch() {
        this.view.setClickable(false);
        this.view.setFocusable(false);
        this.view.setFocusableInTouchMode(false);
    }

    public MapView getMapView() {
        return view;
    }

    public void drawMarker(Marker marker) {
        view.addMarker(marker);
    }

    public void removeMarker(Marker marker) {
        view.removeMarker(marker);
    }

    public void drawOverlay(Overlay path) {
        view.getOverlays().add(path);
    }

    public void removeOverlay(Overlay path) {
       view.getOverlays().remove(path);
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

    public void setZoomBBox(BoundingBox bBox) {
        view.zoomToBoundingBox(bBox, true);
    }
}
