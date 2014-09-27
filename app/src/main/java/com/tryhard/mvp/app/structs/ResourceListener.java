package com.tryhard.mvp.app.structs;

public interface ResourceListener {
    void callback(boolean error, ResourceManager res);
    void before();
    void after();
}
