package com.common.utils;

public final class TimeUtil {

    private TimeUtil(){}

    public static long getNowUnix(){
        return System.currentTimeMillis()/1000L;
    }
}
