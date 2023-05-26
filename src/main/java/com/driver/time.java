package com.driver;

public class time {
    private time(){

    }
    public static int getTime(String time){
        String arr[] = time.split(":");
        return (Integer.valueOf(arr[0])*60) + Integer.valueOf(arr[1]);

    }
    public static String getTime(int time){
        int a = time/60;
        int b = time%60;
        return String.valueOf(a)+":"+String.valueOf(b);


    }
}
