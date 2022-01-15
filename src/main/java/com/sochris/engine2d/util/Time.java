package com.sochris.engine2d.util;

public class Time {

    public static float startedTime = System.nanoTime();

    public static float getTime(){
        return (float)((System.nanoTime() - startedTime) * 1E-9);
    }
    
}
