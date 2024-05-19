package com.common.utils;

public class TimeUtil {

    private TimeUtil(){}

    public static long getNowUnix(){
        return System.currentTimeMillis()/1000L;
    }
}
