package com.tryhard.mvp.app.structs;

/**
 * Created by juancarlos on 09/10/14.
 */
public class Tweet {
    public String author;
    public String text;
    public String dateTime;
    public Tweet() {}
    public Tweet(String author, String text, String dateTime) {
        this.author = author;
        this.text = text;
        this.dateTime = dateTime;
    }
}
