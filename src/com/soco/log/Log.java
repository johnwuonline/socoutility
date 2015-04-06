package com.soco.log;

public class Log {
    
    public static boolean DEBUG_ON = false;

    public static void log(String msg){
        if(DEBUG_ON){
            System.out.println(msg);
        }
    }
    
    public static void error(String msg){
        System.out.println(msg);
    }
}
