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
        busStopsData[0] = new BusStopInformation("301", R.drawable.m_301_v3);
        busStopsData[1] = new BusStopInformation("302", R.drawable.m_302_v2);
        busStopsData[2] = new BusStopInformation("303", R.drawable.m_303_v2);
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
