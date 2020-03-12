package com.TeamT3N.fukiosk;
import android.app.Application;

public class globalconfig extends Application {
    public static String ipaddress = "http://192.168.2.155:8080/";

    private static globalconfig singleton;

    public static globalconfig getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
