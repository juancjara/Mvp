package com.tryhard.mvp.app.structs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Route implements Serializable {
    public int routeId;
    public long busTime;
    public long walkTime;
    public Date nextBus;
    public BusStop from;
    public BusStop to;
    public List<Path> walks;
    public List<Path> paths;

    private String minutesToString(long minutes) {
        long hours = minutes / 60;
        minutes %= 60;
        String ans = "";
        if (hours > 0) {
            ans = hours + "h";
        }
        if (minutes > 0) {
            if (!ans.isEmpty())
                ans += " ";
            ans += minutes + "m";
        }
        if (ans.isEmpty()) {
            ans = "0m";
        }
        return ans;
    }

    public String getWalkTimeStr() {
        return minutesToString(walkTime);
    }

    public String getBusTimeStr() {
        return minutesToString(busTime);
    }
}