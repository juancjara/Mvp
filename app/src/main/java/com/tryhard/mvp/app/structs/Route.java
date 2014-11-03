package com.tryhard.mvp.app.structs;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Route implements Serializable {
    public int routeId;
    public long busTime;
    public long walkTime;
    public Date nextBus;
    public BusStop busStart;
    public BusStop busEnd;
    public BusStop walkStart;
    public BusStop walkEnd;
    public List<Path> paths;
    public List<Path> walks;
    public String busLabel;

    private String secondsToString(long seconds) {
        long minutes = seconds / 60;
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
        return secondsToString(walkTime);
    }

    public String getBusTimeStr() {
        return secondsToString(busTime);
    }

    public boolean hasWalkToDestination() {
        return walkEnd.id != busEnd.id;
    }

    public boolean hasWalkToBusStart() {
        return walkStart.id != busStart.id;
    }
}