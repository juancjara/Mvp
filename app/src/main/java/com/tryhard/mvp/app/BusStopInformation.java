package com.tryhard.mvp.app;

/**
 * Created by juancarlos on 25/09/14.
 */
public class BusStopInformation {
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    private int imageId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public BusStopInformation() {}
    public BusStopInformation(String name,int imageId) {
        this.imageId = imageId;
        this.name = name;
    }
}
