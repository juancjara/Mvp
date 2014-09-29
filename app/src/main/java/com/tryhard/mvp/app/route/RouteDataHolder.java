package com.tryhard.mvp.app.route;

import com.tryhard.mvp.app.R;

/**
 * Created by juancarlos on 28/09/14.
 */
public class RouteDataHolder {
    public static RouteDataHolder instance = new RouteDataHolder();
    public static RouteDataHolder getInstance(){
        return instance;
    }
    int busStopSelected;
    public int getBusStopSelected() {
        return busStopSelected;
    }
    public void setBusStopSelected(int sel) {
        busStopSelected = sel;
    }
    BusStopInformation[] busStopsData;
    private RouteDataHolder() {
        busStopsData = new BusStopInformation[3];
        busStopsData[0] = new BusStopInformation("301", R.drawable.simple_map_301_vertical);
        busStopsData[1] = new BusStopInformation("302", R.drawable.simple_map_301_vertical);
        busStopsData[2] = new BusStopInformation("303", R.drawable.simple_map_303_vertical);
    }
    public BusStopInformation[] getBusStopList(){
        return busStopsData;
    }
    public BusStopInformation getBusStopInfo(int pos) {
        return busStopsData[pos];
    }
    public BusStopInformation getSelectedBusStopInfo() {
        return busStopsData[busStopSelected];
    }
    public int sizeListBusStopInfo(){
        return busStopsData.length;
    }
}
